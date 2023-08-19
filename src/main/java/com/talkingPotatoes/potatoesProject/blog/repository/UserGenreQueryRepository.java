package com.talkingPotatoes.potatoesProject.blog.repository;

import com.talkingPotatoes.potatoesProject.blog.entity.Genre;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserGenreQueryRepository {
    List<String> findByUserId(UUID userId);
}
