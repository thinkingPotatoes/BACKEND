package com.talkingPotatoes.potatoesProject.user.repository;

import com.talkingPotatoes.potatoesProject.user.entity.SimUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SimUserRepository extends JpaRepository<SimUser, UUID> {
    List<SimUser> getSimUsersByUserId(String userId);
}
