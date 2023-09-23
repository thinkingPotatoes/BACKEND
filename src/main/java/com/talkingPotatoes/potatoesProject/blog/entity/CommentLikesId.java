package com.talkingPotatoes.potatoesProject.blog.entity;

import java.io.Serializable;
import java.util.UUID;

public class CommentLikesId implements Serializable {
    private UUID userId;
    private UUID commentId;
}
