package com.talkingPotatoes.potatoesProject.blog.repository;

import com.talkingPotatoes.potatoesProject.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    Long countByArticleId(UUID articleId);
}
