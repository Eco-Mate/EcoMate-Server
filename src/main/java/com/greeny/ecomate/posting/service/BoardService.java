package com.greeny.ecomate.posting.service;

import com.greeny.ecomate.challenge.entity.Challenge;
import com.greeny.ecomate.challenge.repository.ChallengeRepository;
import com.greeny.ecomate.exception.NotFoundException;
import com.greeny.ecomate.posting.dto.BoardDto;
import com.greeny.ecomate.posting.dto.BoardListDto;
import com.greeny.ecomate.posting.dto.CreateBoardRequestDto;
import com.greeny.ecomate.posting.dto.UpdateBoardRequestDto;
import com.greeny.ecomate.posting.entity.Board;
import com.greeny.ecomate.posting.repository.BoardRepository;
import com.greeny.ecomate.user.entity.User;
import com.greeny.ecomate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.NotAcceptableStatusException;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

   private final BoardRepository boardRepository;
   private final UserRepository userRepository;
   private final ChallengeRepository challengeRepository;

   @Transactional
   public Long createBoard(CreateBoardRequestDto createDto) {
      User user = userRepository.findByNickname(createDto.getNickname())
              .orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다."));
      validateChallenge(createDto.getChallengeId());
      Board board = createDto.toEntity(user);
      return boardRepository.save(board).getBoardId();
   }

   public List<BoardDto> getAllBoard() {
      List<Board> boardList = boardRepository.findAll();
      return boardList.stream().map(BoardDto::new).toList();
   }

   public List<BoardDto> getBoardByNickname(String nickname) {
      List<Board> boardList = boardRepository.findAllByUser(
              userRepository.findByNickname(nickname).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다.")
      ));
      return boardList.stream().map(BoardDto::new).toList();
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

}
