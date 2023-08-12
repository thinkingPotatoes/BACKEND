package com.talkingPotatoes.potatoesProject.blog.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talkingPotatoes.potatoesProject.blog.entity.UserGenre;

@Repository
public interface UserGenreRepository extends JpaRepository<UserGenre, UUID> {
}
