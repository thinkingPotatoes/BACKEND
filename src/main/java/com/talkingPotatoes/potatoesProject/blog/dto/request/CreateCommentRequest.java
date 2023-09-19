package com.talkingPotatoes.potatoesProject.blog.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class CreateCommentRequest {
    private UUID articleId;

    private String content;
}
