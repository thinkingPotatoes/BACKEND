package com.talkingPotatoes.potatoesProject.blog.repository;

import com.talkingPotatoes.potatoesProject.blog.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    Long countByArticleId(UUID articleId);

    boolean existsById(UUID id);

    boolean existsByUserIdAndId(UUID userId, UUID id);

    Page<Comment> findAllByArticleId(UUID articleId, Pageable pageable);

    void deleteAllByArticleId(UUID articleId);
}
