package com.talkingPotatoes.potatoesProject.blog.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class ArticleSearchDto {
    private UUID id;

    private String subject;

    private String content;

    private String poster;

    private Integer grade;

    private LocalDateTime watchedAt;

    private Long likeCnt;

    private Long commentCnt;
}
