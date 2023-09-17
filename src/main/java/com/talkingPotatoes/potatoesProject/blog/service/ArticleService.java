package com.talkingPotatoes.potatoesProject.blog.service;

import com.talkingPotatoes.potatoesProject.blog.dto.ArticleDto;
import com.talkingPotatoes.potatoesProject.blog.dto.ArticleSearchDto;
import com.talkingPotatoes.potatoesProject.blog.dto.CalendarDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ArticleService {
    void createArticle(ArticleDto articleDto);

    void updateArticle(ArticleDto articleDto);

    void deleteArticle(UUID userId, UUID id);

    ArticleDto searchArticleById(UUID id);

    void updateLikes(UUID id, UUID articleId);

    Page<ArticleDto> searchArticleByMovieId(String movieId, Pageable pageable);

    Page<ArticleDto> searchArticleByUserId(UUID userId, Pageable pageable);

    Page<ArticleSearchDto> searchArticleByUserIdAndKeyword(UUID userId, String keyword, Pageable pageable);

    List<CalendarDto> getCalendarFromMonth(UUID userId, Integer year, Integer month);

    List<CalendarDto> getCalendarFromDay(UUID userId, Integer year, Integer month, Integer day);
}
