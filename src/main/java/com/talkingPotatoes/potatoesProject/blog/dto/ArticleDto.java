package com.talkingPotatoes.potatoesProject.blog.dto;

import com.talkingPotatoes.potatoesProject.blog.entity.Scope;
import com.talkingPotatoes.potatoesProject.common.dto.BaseDto;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@SuperBuilder
public class ArticleDto extends BaseDto {
    private UUID id;

    private UUID userId;

    private String movieId;   // FIX: movieDto

    private String subject;

    private String content;

    private Integer grade;

    private Scope scope;

    private String theater;

    private String seat;

    private Boolean spoiler;

    private Long likeCnt;

    private LocalDateTime watchedAt;
}
