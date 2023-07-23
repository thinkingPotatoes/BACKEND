package com.talkingPotatoes.potatoesProject.blog.repository;


import com.talkingPotatoes.potatoesProject.blog.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ArticleRepository extends JpaRepository<Article, UUID> {
    boolean existsById(UUID id);
    List<Article> findAllByMovieId(UUID movieId);
    List<Article> findAllByUserId(UUID userId);
}