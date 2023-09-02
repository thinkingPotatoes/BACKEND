package com.talkingPotatoes.potatoesProject.blog.repository;

import com.talkingPotatoes.potatoesProject.blog.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    Boolean existsByUserIdAndArticleIdAndClickedIsTrue(UUID userId, UUID articleId);

    Long countByArticleIdAndClickedIsTrue(UUID articleId);

    void deleteByUserIdAndArticleIdAndClickedIsFalseAndUpdatedAt(UUID userId, UUID articleId, LocalDateTime updatedAt);
}
