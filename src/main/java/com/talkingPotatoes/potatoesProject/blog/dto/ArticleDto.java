package com.talkingPotatoes.potatoesProject.blog.dto;

import com.talkingPotatoes.potatoesProject.blog.entity.ArticleTime;
import com.talkingPotatoes.potatoesProject.blog.entity.Scope;
import com.talkingPotatoes.potatoesProject.common.dto.BaseDto;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@SuperBuilder
public class ArticleDto extends BaseDto {
    private UUID id;

    private UUID userId;

    private String movieId;

    private String subject;

    private String content;

    private Float star;

    private Scope scope;

    private String theater;

    private String seat;

    private Boolean spoiler;

    private LocalDate watchedAt;

    private ArticleTime watchedTime;

    private LocalDateTime createdAt;

    private String poster;

    private Long likeCnt;

    private Long commentCnt;
}
