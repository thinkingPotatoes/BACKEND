package com.talkingPotatoes.potatoesProject.blog.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class GetCommentResponse {
    private UUID id;

    private UUID parentId;

    private UUID articleId;

    private String nickname;

    private UUID userId;

    private String content;

    private Long likeCnt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;
}
