package com.talkingPotatoes.potatoesProject.user.repository;

import java.time.LocalDateTime;
import java.util.List;

import java.util.Optional;

import java.util.UUID;

import com.talkingPotatoes.potatoesProject.user.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talkingPotatoes.potatoesProject.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findByRoleAndUpdatedAtIsLessThan(Role role, LocalDateTime dateTime);

    Optional<User> findByUserId(String userId);

    Optional<User> findByUserIdAndPassword(String userId, String encode);

    boolean existsUserByUserIdAndRole(String userId, Role role);
}
