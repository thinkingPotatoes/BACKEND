package com.talkingPotatoes.potatoesProject.blog.entity;

import com.talkingPotatoes.potatoesProject.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Article extends BaseEntity {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private UUID movieId;

    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ColumnDefault("0")
    private Float star;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'PUBLIC'")
    private Scope scope;

    private String theater;

    private String seat;

    private Boolean spoiler;

    private Long likeCnt;

    private LocalDateTime watchedAt;
}
