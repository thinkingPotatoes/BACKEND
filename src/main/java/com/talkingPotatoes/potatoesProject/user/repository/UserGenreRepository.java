package com.talkingPotatoes.potatoesProject.user.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talkingPotatoes.potatoesProject.user.entity.UserGenre;

@Repository
public interface UserGenreRepository extends JpaRepository<UserGenre, UUID> {
}
