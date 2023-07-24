package com.greeny.ecomate.posting.service;

import com.greeny.ecomate.challenge.entity.Challenge;
import com.greeny.ecomate.challenge.repository.ChallengeRepository;
import com.greeny.ecomate.exception.NotFoundException;
import com.greeny.ecomate.posting.dto.BoardDto;
import com.greeny.ecomate.posting.dto.CreateBoardRequestDto;
import com.greeny.ecomate.posting.dto.UpdateBoardRequestDto;
import com.greeny.ecomate.posting.entity.Board;
import com.greeny.ecomate.posting.repository.BoardRepository;
import com.greeny.ecomate.s3.service.AwsS3Service;
import com.greeny.ecomate.user.entity.User;
import com.greeny.ecomate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

   @Value("${s3-directory.board}")
   String boardDirectory;

   @Value("${cloud.aws.s3.url}")
   String s3Url;


   private final BoardRepository boardRepository;
   private final UserRepository userRepository;
   private final ChallengeRepository challengeRepository;
   private final AwsS3Service awsS3Service;

   @Transactional
   public Long createBoard(CreateBoardRequestDto createDto, MultipartFile file) {
      User user = userRepository.findByNickname(createDto.getNickname())
              .orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다."));
      validateChallenge(createDto.getChallengeId());

      String fileName = uploadImage(file);
      Board board = Board.builder()
              .user(user)
              .boardTitle(createDto.getBoardTitle())
              .boardContent(createDto.getBoardContent())
              .challengeId(createDto.getChallengeId())
              .image(fileName)
              .likeCnt(0L)
              .build();

      return boardRepository.save(board).getBoardId();
   }

   public List<BoardDto> getAllBoard() {
      List<Board> boardList = boardRepository.findAll();
      return boardList.stream().map(this::createBoardDto).toList();
   }

   public List<BoardDto> getBoardByNickname(String nickname) {
      List<Board> boardList = boardRepository.findAllByUser(
              userRepository.findByNickname(nickname).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다.")
      ));
      return boardList.stream().map(this::createBoardDto).toList();
   }

   @Transactional
   public Long updateBoard(UpdateBoardRequestDto updateDto) {
      Board board = boardRepository.findById(updateDto.getBoardId())
              .orElseThrow(() -> new NotFoundException("존재하지 않는 게시글입니다."));
      board.update(updateDto.getBoardTitle(), updateDto.getBoardContent());
      return board.getBoardId();
   }

   private void validateChallenge(Long challengeId) {
      // challengeId == 0 : challenge 미등록
      if (challengeId != 0) {
         challengeRepository.findById(challengeId)
                 .orElseThrow(() -> new NotFoundException("존재하지 않는 챌린지입니다."));
      }
   }

   private String uploadImage(MultipartFile file){
      return awsS3Service.upload(boardDirectory, file);
   }

   private BoardDto createBoardDto(Board board) {
      Long challengeId = board.getChallengeId();
      if (challengeId != 0) {
         Challenge challenge = challengeRepository.findById(challengeId)
                 .orElseThrow(() -> new NotFoundException("존재하지 않는 챌린지입니다."));
         return new BoardDto(board, challenge.getChallengeTitle(), s3Url, boardDirectory);
      }
      return new BoardDto(board, null, s3Url, boardDirectory);
   }

}
