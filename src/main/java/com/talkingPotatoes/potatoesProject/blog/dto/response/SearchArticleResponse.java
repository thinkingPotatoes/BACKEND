package com.talkingPotatoes.potatoesProject.blog.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class SearchArticleResponse {
    private UUID id;

    private String subject;

    private String content;

    private String poster;

    private Integer grade;

    private LocalDateTime watchedAt;

    private Long likesCnt;

    private Long commentCnt;
}
