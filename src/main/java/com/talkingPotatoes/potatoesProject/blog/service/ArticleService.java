package com.talkingPotatoes.potatoesProject.blog.service;

import com.talkingPotatoes.potatoesProject.blog.dto.ArticleDto;
import com.talkingPotatoes.potatoesProject.blog.dto.ArticleSearchDto;
import com.talkingPotatoes.potatoesProject.blog.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ArticleService {
    void createArticle(ArticleDto articleDto);
    void updateArticle(ArticleDto articleDto);
    void deleteArticle(UUID userId, UUID id);
    ArticleDto searchArticleById(UUID id);
    List<ArticleDto> searchArticleByMovieId(String movieId);
    List<ArticleDto> searchArticleByUserId(UUID userId);
    Page<ArticleSearchDto> searchArticleByUserIdAndKeyword(UUID userId, String keyword, Pageable pageable);
}
