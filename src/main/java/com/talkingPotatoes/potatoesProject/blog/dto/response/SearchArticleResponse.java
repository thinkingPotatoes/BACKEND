package com.talkingPotatoes.potatoesProject.blog.dto.response;

import com.talkingPotatoes.potatoesProject.blog.entity.ArticleTime;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class SearchArticleResponse {
    private UUID id;

    private String subject;

    private String movieId;

    private String content;

    private String poster;

    private Float star;

    private LocalDate watchedAt;

    private ArticleTime watchedTime;

    private Long likeCnt;

    private Long commentCnt;

    private LocalDateTime createdAt;
}
