package com.talkingPotatoes.potatoesProject.blog.entity;

import java.io.Serializable;
import java.util.UUID;

public class LikesId implements Serializable {
    private UUID userId;
    private UUID articleId;
}
