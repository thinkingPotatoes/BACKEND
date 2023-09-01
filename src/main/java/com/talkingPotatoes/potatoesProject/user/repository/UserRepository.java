package com.talkingPotatoes.potatoesProject.user.repository;

import java.time.LocalDateTime;
import java.util.List;

import java.util.Optional;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talkingPotatoes.potatoesProject.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findByEmailCheckedIsFalseAndUpdatedAtIsLessThan(LocalDateTime dateTime);
  
//    void deleteByEmailCheckedIsFalseAndUpdatedAtIsGreaterThan(LocalDateTime dateTime);
  
    Optional<User> findByUserId(String userId);

    Optional<User> findByUserIdAndPassword(String userId, String encode);

    Boolean existsByNickname(String nickname);
}
