package com.greeny.ecomate.comment.service;

import com.greeny.ecomate.comment.dto.CommentDto;
import com.greeny.ecomate.comment.dto.CommentListDto;
import com.greeny.ecomate.comment.dto.CreateCommentRequestDto;
import com.greeny.ecomate.comment.entity.Comment;
import com.greeny.ecomate.comment.repository.CommentRepository;
import com.greeny.ecomate.exception.NotFoundException;
import com.greeny.ecomate.posting.entity.Board;
import com.greeny.ecomate.posting.repository.BoardRepository;
import com.greeny.ecomate.member.entity.Member;
import com.greeny.ecomate.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    @Value("${s3-directory.profile}")
    String profileDirectory;

    @Value("${cloud.aws.s3.url}")
    String s3Url;

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public Long createComment(CreateCommentRequestDto createRequest, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다."));

        Board board = boardRepository.findById(createRequest.getBoardId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시물입니다."));

        Comment comment = Comment.builder()
                .memberId(member.getMemberId())
                .board(board)
                .content(createRequest.getContent())
                .build();

        return commentRepository.save(comment).getCommentId();
    }

    public CommentListDto getCommentByBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시물입니다."));
        List<Comment> commentList = commentRepository.findAllByBoard(board);
        return new CommentListDto(commentList.stream().map(this::toCommentDto).collect(Collectors.toList()));
    }

    private CommentDto toCommentDto(Comment comment) {
        Member member = memberRepository.findById(comment.getMemberId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다."));

        return new CommentDto(member, comment);
    }

    public void deleteCommentById(Long commentId, Long memberId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 댓글입니다."));
        if (!comment.getMemberId().equals(memberId))
            throw new AccessDeniedException("삭제 권한이 없습니다.");

        commentRepository.delete(comment);
    }
}
