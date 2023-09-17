package com.talkingPotatoes.potatoesProject.user.repository;

import com.talkingPotatoes.potatoesProject.user.entity.UserGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserGenreRepository extends JpaRepository<UserGenre, UUID> {
    void deleteByUserId(UUID userId);
}
