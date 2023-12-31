package com.talkingPotatoes.potatoesProject.blog.repository;


import com.talkingPotatoes.potatoesProject.blog.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ArticleRepository extends JpaRepository<Article, UUID> {
    boolean existsById(UUID id);
    boolean existsByUserIdAndId(UUID userId, UUID id);
    Page<Article> findAllByMovieId(String movieId, Pageable pageable);
    Page<Article> findAllByUserId(UUID userId, Pageable pageable);
}