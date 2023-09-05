package com.greeny.ecomate.map.service;

import com.greeny.ecomate.exception.NotFoundException;
import com.greeny.ecomate.exception.UnauthorizedAccessException;
import com.greeny.ecomate.map.dto.CreateEcoStoreRequestDto;
import com.greeny.ecomate.map.dto.EcoStoreDto;
import com.greeny.ecomate.map.dto.MemberLocationDto;
import com.greeny.ecomate.map.dto.UpdateEcoStoreRequestDto;
import com.greeny.ecomate.map.entity.EcoStore;
import com.greeny.ecomate.map.entity.StoreLike;
import com.greeny.ecomate.map.repository.EcoStoreRepository;
import com.greeny.ecomate.map.repository.StoreLikeRepository;
import com.greeny.ecomate.member.entity.Member;
import com.greeny.ecomate.member.entity.Role;
import com.greeny.ecomate.member.repository.MemberRepository;
import com.greeny.ecomate.s3.service.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EcoStoreService {

    @Value("${s3-directory.store}")
    String storeDirectory;

    @Value("${cloud.aws.s3.url}")
    String s3Url;

    private final EcoStoreRepository ecoStoreRepository;
    private final MemberRepository memberRepository;
    private final StoreLikeRepository storeLikeRepository;

    private final AwsS3Service awsS3Service;

    @Transactional
    public Long createEcoStore(CreateEcoStoreRequestDto createDto, MultipartFile file, Long memberId) {
        validateAuth(memberId);

        String fileName = uploadImage(file);
        EcoStore ecoStore = EcoStore.builder()
                .memberId(memberId)
                .storeName(createDto.getStoreName())
                .description(createDto.getDescription())
                .image(fileName)
                .latitude(createDto.getLatitude())
                .longitude(createDto.getLongitude())
                .address(createDto.getAddress())
                .likeCnt(0L)
                .build();

        ecoStoreRepository.save(ecoStore);
        return ecoStore.getStoreId();
    }

    public List<EcoStoreDto> getAllEcoStores(Long memberId) {
        List<EcoStore> ecoStores = ecoStoreRepository.findAll();
        return ecoStores.stream().map(e->createEcoStoreDto(e, memberId)).toList();
    }

    public EcoStoreDto getEcoStoreById(Long storeId, Long memberId) {
        EcoStore ecoStore = findEcoStoreById(storeId);
        return createEcoStoreDto(ecoStore, memberId);
    }

    public List<EcoStoreDto> getEcoStoresByMemberLocation(Double latitude, Double longitude, Long memberId) {
        List<EcoStore> ecoStores = ecoStoreRepository.findEcoStoresByMemberLocation(latitude, longitude);
        return ecoStores.stream().map(e -> createEcoStoreDto(e, memberId)).toList();
    }

    public List<EcoStoreDto> getAllLikedEcoStoresByCurrentMember(Long memberId) {
        List<StoreLike> storeLikeList = storeLikeRepository.findByMemberId(memberId);
        return storeLikeList.stream().map(s -> createEcoStoreDto(s.getEcoStore(), memberId)).toList();
    }

    public List<EcoStoreDto> getAllLikedEcoStoresByCurrentMemberLocation(Double latitude, Double longitude, Long memberId) {
        List<EcoStore> ecoStores = ecoStoreRepository.findEcoStoresByMemberLocation(latitude, longitude);
        List<EcoStore> likeEcoStores = storeLikeRepository.findByMemberId(memberId).stream().map(StoreLike::getEcoStore).toList();
        return ecoStores.stream().filter(likeEcoStores::contains).map(e -> createEcoStoreDto(e, memberId)).toList();
    }

    @Transactional
    public Long updateEcoStore(Long storeId, UpdateEcoStoreRequestDto dto, Long memberId) {
        validateAuth(memberId);
        EcoStore ecoStore = findEcoStoreById(storeId);
        ecoStore.update(dto.getStoreName(), dto.getDescription(), dto.getLatitude(), dto.getLatitude(), dto.getAddress());
        return ecoStore.getStoreId();
    }

    @Transactional
    public String deleteEcoStore(Long storeId, Long memberId) {
        validateAuth(memberId);
        ecoStoreRepository.deleteById(storeId);
        return "해당 에코 매장이 삭제되었습니다.";
    }

    private void validateAuth(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다."));

        if(member.getRole() != Role.ROLE_ADMIN)
            throw new UnauthorizedAccessException("권한이 없습니다.");
    }

    private String uploadImage(MultipartFile file) { return awsS3Service.upload(storeDirectory, file); }

    private EcoStoreDto createEcoStoreDto(EcoStore ecoStore, Long memberId) {
        Boolean liked = storeLikeRepository.findByEcoStoreAndMemberId(ecoStore, memberId).isPresent();
        return new EcoStoreDto(ecoStore, liked);
    }

    private EcoStore findEcoStoreById(Long storeId) {
        return ecoStoreRepository.findById(storeId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 에코 매장입니다."));
    }

}
