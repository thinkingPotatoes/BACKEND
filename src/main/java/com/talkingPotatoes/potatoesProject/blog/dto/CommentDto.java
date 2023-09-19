package com.talkingPotatoes.potatoesProject.blog.dto;

import com.talkingPotatoes.potatoesProject.common.dto.BaseDto;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
public class CommentDto extends BaseDto {
    private UUID id;

    private UUID articleId;

    private UUID userId;

    private String content;

    private String nickName;
}
