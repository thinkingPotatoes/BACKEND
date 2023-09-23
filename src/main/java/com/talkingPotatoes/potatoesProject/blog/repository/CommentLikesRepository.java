package com.talkingPotatoes.potatoesProject.blog.repository;

import com.talkingPotatoes.potatoesProject.blog.entity.CommentLikes;
import com.talkingPotatoes.potatoesProject.blog.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface CommentLikesRepository extends JpaRepository<CommentLikes, Long> {
    Boolean existsByUserIdAndCommentIdAndClickedIsTrue(UUID userId, UUID articleId);

    Long countByCommentIdAndClickedIsTrue(UUID articleId);

    void deleteByUserIdAndCommentIdAndClickedIsFalseAndUpdatedAt(UUID userId, UUID articleId, LocalDateTime updatedAt);

    List<Likes> findByClickedAndUpdatedAtIsLessThan(boolean clicked, LocalDateTime dateTime);
}
