package com.talkingPotatoes.potatoesProject.blog.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class CommentLikesId implements Serializable {
    private UUID userId;
    private UUID commentId;
}
