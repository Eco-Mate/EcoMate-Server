package com.greeny.ecomate.member.service;

import com.greeny.ecomate.exception.NotFoundException;
import com.greeny.ecomate.member.dto.CreateMemberRequestDto;
import com.greeny.ecomate.member.dto.MemberDto;
import com.greeny.ecomate.member.dto.UpdateMemberRequestDto;
import com.greeny.ecomate.member.entity.Member;
import com.greeny.ecomate.member.repository.MemberRepository;
import com.greeny.ecomate.s3.service.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    @Value("${s3-directory.profile}")
    private String profileImageDirectory;

    private final MemberRepository memberRepository;
    private final AwsS3Service awsS3Service;

    public Long createMember(CreateMemberRequestDto createDto) {
        return memberRepository.save(createDto.toEntity()).getMemberId();
    }

    public List<MemberDto> getAllMember() {
        List<Member> memberList = memberRepository.findAll();
        return memberList.stream().map(MemberDto::from).toList();
    }

    public Member getMemberById(Long memberId) {
        return findMemberById(memberId);
    }

    public MemberDto getCurrentMember(Long memberId) {
        return MemberDto.from(findMemberById(memberId));
    }

    @Transactional
    public Long updateMember(UpdateMemberRequestDto updateDto, Long memberId) {
        Member member = findMemberById(memberId);
        member.update(updateDto.getNickname(), updateDto.getEmail(), updateDto.getStatusMessage());

        return member.getMemberId();
    }

    @Transactional
    public Long updateProfileImage(MultipartFile profileImage, Long memberId) {
        String fileName = uploadProfileImage(profileImage);
        Member member = findMemberById(memberId);
        if (member.getProfileImage() != null) {
            awsS3Service.delete(profileImageDirectory, member.getProfileImage());
        }
        member.updateProfileImage(fileName);

        return member.getMemberId();
    }

    private String uploadProfileImage(MultipartFile profileImage) {
        return awsS3Service.upload(profileImageDirectory, profileImage);
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다."));
    }
}
