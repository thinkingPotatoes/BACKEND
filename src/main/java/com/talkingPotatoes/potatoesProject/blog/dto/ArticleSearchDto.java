package com.talkingPotatoes.potatoesProject.blog.dto;

import com.talkingPotatoes.potatoesProject.blog.entity.Scope;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class ArticleSearchDto {
    private UUID id;

    private UUID userId;

    private String movieId;

    private String subject;

    private String content;

    private Integer grade;

    private Scope scope;

    private String theater;

    private String seat;

    private Boolean spoiler;

    private LocalDateTime watchedAt;

    private String poster;

    private Long likeCnt;

    private Long commentCnt;
}
