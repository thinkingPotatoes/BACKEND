package com.talkingPotatoes.potatoesProject.blog.repository;

import com.talkingPotatoes.potatoesProject.blog.dto.CalendarDto;
import com.talkingPotatoes.potatoesProject.blog.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ArticleQueryRepository {
    Page<Article> findByUserIdAndKeyword(UUID userId, String keyword, Pageable pageable);

    List<CalendarDto> findByBetweenDate(UUID userId, LocalDate firstDate, LocalDate lastDate);

    List<CalendarDto> findByDate(UUID userId, LocalDate date);
}
