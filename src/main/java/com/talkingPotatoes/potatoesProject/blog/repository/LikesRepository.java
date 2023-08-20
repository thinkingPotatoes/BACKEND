package com.talkingPotatoes.potatoesProject.blog.repository;

import com.talkingPotatoes.potatoesProject.blog.entity.Comment;
import com.talkingPotatoes.potatoesProject.blog.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    Boolean existsByUserIdAndArticleId(UUID userId, UUID articleId);

    Long countByArticleId(UUID articleId);

    void deleteByUserIdAndArticleId(UUID userId, UUID articleId);
}
