package com.greeny.ecomate.posting.service;

import com.greeny.ecomate.challenge.entity.Challenge;
import com.greeny.ecomate.challenge.repository.ChallengeRepository;
import com.greeny.ecomate.exception.NotFoundException;
import com.greeny.ecomate.like.entity.Like;
import com.greeny.ecomate.like.repository.LikeRepository;
import com.greeny.ecomate.posting.dto.BoardDto;
import com.greeny.ecomate.posting.dto.CreateBoardRequestDto;
import com.greeny.ecomate.posting.dto.UpdateBoardRequestDto;
import com.greeny.ecomate.posting.entity.Board;
import com.greeny.ecomate.posting.repository.BoardRepository;
import com.greeny.ecomate.s3.service.AwsS3Service;
import com.greeny.ecomate.member.entity.Member;
import com.greeny.ecomate.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

   @Value("${s3-directory.board}")
   String boardDirectory;

   @Value("${cloud.aws.s3.url}")
   String s3Url;


   private final BoardRepository boardRepository;
   private final MemberRepository memberRepository;
   private final ChallengeRepository challengeRepository;
   private final LikeRepository likeRepository;
   private final AwsS3Service awsS3Service;

   @Transactional
   public Board createBoard(CreateBoardRequestDto createDto, MultipartFile file, Long memberId) {
      Member member = memberRepository.findById(memberId)
              .orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다."));
      validateChallenge(createDto.getChallengeId());

      String fileName = uploadImage(file);
      Board board = Board.builder()
              .member(member)
              .boardTitle(createDto.getBoardTitle())
              .boardContent(createDto.getBoardContent())
              .challengeId(createDto.getChallengeId())
              .image(fileName)
              .likeCnt(0L)
              .build();

      return boardRepository.save(board);
   }

   public List<BoardDto> getAllBoard(Long memberId) {
      List<Board> boardList = boardRepository.findAll();
      return boardList.stream().map(b -> createBoardDto(b, memberId)).toList();
   }

   public List<BoardDto> getAllSortedByLikeCnt(Long memberId) {
      List<Board> boardList = boardRepository.findAllSortedByLikeCnt();
      return boardList.stream().map(b -> createBoardDto(b, memberId)).toList();
   }

   @Transactional
   public Long updateBoard(Long boardId, Long memberId, UpdateBoardRequestDto updateDto) {
      Board board = findBoardById(boardId);
      validateAuth(board, memberId);
      board.update(updateDto.getBoardTitle(), updateDto.getBoardContent());
      return board.getBoardId();
   }

   @Transactional
   public void deleteBoardById(Long boardId, Long memberId) {
      Board board = findBoardById(boardId);
      validateAuth(board, memberId);
      boardRepository.delete(board);
   }

   private void validateChallenge(Long challengeId) {
      // challengeId == 0 : challenge 미등록
      if (challengeId != 0) {
         challengeRepository.findById(challengeId)
                 .orElseThrow(() -> new NotFoundException("존재하지 않는 챌린지입니다."));
      }
   }

   private void validateAuth(Board board, Long memberId) {
      if (!board.getMember().getMemberId().equals(memberId))
         throw new AccessDeniedException("삭제 권한이 없습니다.");
   }

   private String uploadImage(MultipartFile file){
      return awsS3Service.upload(boardDirectory, file);
   }

   private BoardDto createBoardDto(Board board, Long memberId) {
      String imageUrl = s3Url + "/" + boardDirectory;
      Boolean liked = likeRepository.findByBoardAndMemberId(board, memberId).isPresent();

      Long challengeId = board.getChallengeId();
      if (challengeId != 0) {
         Challenge challenge = challengeRepository.findById(challengeId)
                 .orElseThrow(() -> new NotFoundException("존재하지 않는 챌린지입니다."));
         return new BoardDto(board, challenge.getChallengeTitle(), imageUrl, liked);
      }
      return new BoardDto(board, null, imageUrl, liked);
   }

    private Board findBoardById(Long boardId) {
      return boardRepository.findById(boardId)
              .orElseThrow(() -> new NotFoundException("존재하지 않는 게시물입니다."));
    }
}
