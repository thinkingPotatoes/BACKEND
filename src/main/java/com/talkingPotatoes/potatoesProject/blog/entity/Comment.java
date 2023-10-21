package com.talkingPotatoes.potatoesProject.blog.entity;

import com.talkingPotatoes.potatoesProject.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @UuidGenerator
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

    @Column(nullable = false)
    private UUID articleId;

    @Column(nullable = false)
    private UUID userId;

    private String content;

    @ColumnDefault("0")
    private Long likeCnt;

    private LocalDateTime deletedAt;

    public void updateCommentLikeCnt(int likeCnt) {
        this.likeCnt += likeCnt;
    }

    public void updateParent(Comment comment) {
        this.parent = comment;
    }

    public void updateDeletedAt() {
        this.deletedAt = LocalDateTime.now();
    }
}
