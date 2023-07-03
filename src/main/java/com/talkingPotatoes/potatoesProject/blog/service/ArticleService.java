package com.talkingPotatoes.potatoesProject.blog.service;

import com.talkingPotatoes.potatoesProject.blog.dto.ArticleDto;
import com.talkingPotatoes.potatoesProject.blog.entity.Article;

import java.util.UUID;

public interface ArticleService {
    boolean existArticleById(UUID id);
    void createArticle(ArticleDto articleDto);
    void updateArticle(ArticleDto articleDto);
    void deleteArticle(UUID id);
    ArticleDto searchArticleById(UUID id);
}
