package com.talkingPotatoes.potatoesProject.blog.dto;

import com.talkingPotatoes.potatoesProject.common.dto.BaseDto;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@SuperBuilder
public class CommentDto extends BaseDto {
    private UUID id;

    private UUID parentId;

    private UUID articleId;

    private UUID userId;

    private String content;

    private String nickName;

    private Long likeCnt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;
}
