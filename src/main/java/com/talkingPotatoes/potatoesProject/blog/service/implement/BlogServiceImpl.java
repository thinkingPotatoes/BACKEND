package com.talkingPotatoes.potatoesProject.blog.service.implement;

import com.talkingPotatoes.potatoesProject.blog.dto.BlogDto;
import com.talkingPotatoes.potatoesProject.blog.service.BlogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class BlogServiceImpl implements BlogService {
    @Override
    public BlogDto get(UUID loginId) {
        return null;
    }
}
