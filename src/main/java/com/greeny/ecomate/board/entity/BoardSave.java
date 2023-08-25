package com.greeny.ecomate.board.entity;

import com.greeny.ecomate.base.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardSave extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_save_id")
    private Long boardSaveId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(name = "member_id")
    private Long memberId;

    @Builder
    public BoardSave(Board board, Long memberId) {
        this.board = board;
        this.memberId = memberId;
    }


}
