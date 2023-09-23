package com.talkingPotatoes.potatoesProject.blog.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class LikesId implements Serializable {
    private UUID userId;
    private UUID articleId;
}
