package com.talkingPotatoes.potatoesProject.user.repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserGenreQueryRepository {
    List<String> findByUserId(UUID userId);
}
