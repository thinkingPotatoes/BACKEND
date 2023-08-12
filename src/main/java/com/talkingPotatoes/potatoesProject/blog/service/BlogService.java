package com.talkingPotatoes.potatoesProject.blog.service;

import com.talkingPotatoes.potatoesProject.blog.dto.BlogDto;

import java.util.UUID;

public interface BlogService {
    BlogDto get(UUID loginId);
}
