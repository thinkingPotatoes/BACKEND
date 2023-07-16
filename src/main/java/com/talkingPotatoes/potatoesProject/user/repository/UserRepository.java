package com.talkingPotatoes.potatoesProject.user.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talkingPotatoes.potatoesProject.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUserId(String userId);

    Optional<User> findByUserIdAndPassword(String userId, String encode);
}
