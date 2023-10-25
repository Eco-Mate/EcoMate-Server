package com.greeny.ecomate.board.service;

import com.greeny.ecomate.board.dto.BoardDto;
import com.greeny.ecomate.board.dto.UpdateBoardRequestDto;
import com.greeny.ecomate.board.entity.Board;
import com.greeny.ecomate.board.repository.BoardRepository;
import com.greeny.ecomate.challenge.entity.Challenge;
import com.greeny.ecomate.challenge.repository.ChallengeRepository;
import com.greeny.ecomate.exception.NotFoundException;
import com.greeny.ecomate.like.entity.Like;
import com.greeny.ecomate.like.repository.LikeRepository;
import com.greeny.ecomate.member.entity.Member;
import com.greeny.ecomate.member.entity.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.*;


@ExtendWith(MockitoExtension.class)
public class BoardServiceTest {

    @InjectMocks
    private BoardService boardService;

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private ChallengeRepository challengeRepository;



    @DisplayName("전체 게시물을 조회하면, 모든 게시물 정보를 반환한다.")
    @Test
    void given_whenSearchingAllBoards_thenReturnsAllBoards() {
        // Given
        Long testMemberId = 1L;
        Board expectedBoard = createBoard();
        given(boardRepository.findAll()).willReturn(List.of(expectedBoard));
        given(likeRepository.findByBoardAndMemberId(expectedBoard, testMemberId)).willReturn(Optional.of(createLike(expectedBoard)));
        given(challengeRepository.findById(1L)).willReturn(Optional.of(createChallenge()));

        // When
        List<BoardDto> boardList = boardService.getAllBoards(testMemberId);

        // Then
        assertThat(boardList)
                .hasSize(1);
        then(boardRepository).should().findAll();
    }

    @DisplayName("게시물의 제목, 본문 수정 정보를 입력하면, 게시물의 제목, 본문을 수정한다.")
    @Test
    void givenModifiedBoardInfo_whenUpdateBoard_thenUpdatesBoard() {
        // Given
        Long testBoardId = 1L;
        Long testMemberId = 1L;
        String updateTitle = "newTitle";
        String updateContent = "newContent";
        UpdateBoardRequestDto updateDto = createUpdateDto(updateTitle, updateContent);
        given(boardRepository.findById(testBoardId)).willReturn(Optional.of(createBoard()));


        // When
        Board board = boardService.updateBoard(testBoardId, testMemberId, updateDto);

        // Then
        assertThat(board)
                .hasFieldOrPropertyWithValue("boardTitle", updateTitle)
                .hasFieldOrPropertyWithValue("boardContent", updateContent);
    }


    @DisplayName("수정하려는 게시물이 존재하지 않으면, 예외를 던진다.")
    @Test
    void givenNonexistentModifiedBoardInfo_whenUpdateBoard_thenThrowsException() {
        // Given
        Long testBoardId = 1L;
        Long testMemberId = 1L;
        UpdateBoardRequestDto updateDto = createUpdateDto("newTitle", "newContent");
        given(boardRepository.findById(testBoardId)).willReturn(Optional.empty());

        // When
        Throwable t = catchThrowable(() -> boardService.updateBoard(testBoardId, testMemberId, updateDto));

        // Then
        assertThat(t)
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 게시물입니다.");
        then(boardRepository).should().findById(testBoardId);

    }

    @DisplayName("삭제할 게시물의 ID를 입력하면, 해당 게시물을 삭제한다.")
    @Test
    void givenBoardId_whenDeletingBoard_thenDeletesBoard() {
        // Given
        Long testBoardId = 1L;
        Long testMemberId = 1L;
        Board board = createBoard();
        given(boardRepository.findById(testBoardId)).willReturn(Optional.of(board));

        assert board != null;
        willDoNothing().given(boardRepository).delete(board);

        // When
        boardService.deleteBoardById(testBoardId, testMemberId);

        // Then
        then(boardRepository).should().delete(board);

    }

    @DisplayName("삭제하려는 게시물이 존재하지 않으면, 예외를 던진다.")
    @Test
    void givenNonexistentBoardId_whenDeletingBoard_thenThrowsException() {
        // Given
        Long testBoardId = 1L;
        Long testMemberId = 1L;
        given(boardRepository.findById(testBoardId)).willReturn(Optional.empty());

        // When
        Throwable t = catchThrowable(() -> boardService.deleteBoardById(testBoardId, testMemberId));

        // Then
        assertThat(t)
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 게시물입니다.");
        then(boardRepository).should().findById(testBoardId);
    }

    private UpdateBoardRequestDto createUpdateDto(String updateTitle, String updateContent) {
        return new UpdateBoardRequestDto(updateTitle, updateContent);
    }

    private Member createMember() {
        Member member = Member.builder()
                .name("testName")
                .nickname("testNickname")
                .email("test@example.com")
                .totalTreePoint(0L)
                .level("씨앗")
                .role(Role.ROLE_USER)
                .build();
        ReflectionTestUtils.setField(member, "memberId", 1L);

        return member;
    }

    private Board createBoard() {
        Board board = Board.builder()
                .boardTitle("title")
                .boardContent("content")
                .challengeId(1L)
                .image("testImage.png")
                .likeCnt(0L)
                .member(createMember())
                .build();
        ReflectionTestUtils.setField(board, "boardId", 1L);
        return board;
    }

    private Challenge createChallenge() {
        Challenge challenge = Challenge.builder()
                .challengeTitle("대중교통 이용하기")
                .description("test")
                .treePoint(100L)
                .goalCnt(100L)
                .activeYn(true)
                .build();
        ReflectionTestUtils.setField(challenge, "challengeId", 1L);
        return challenge;
    }

    private Like createLike(Board board) {
        Like like = new Like(1L, board);
        ReflectionTestUtils.setField(like, "likeId", 1L);
        return like;
    }
}
