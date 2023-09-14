package com.talkingPotatoes.potatoesProject.blog.dto.response;

import com.talkingPotatoes.potatoesProject.blog.entity.ArticleTime;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class GetCalendarDayResponse {
    private UUID id;

    private String subject;

    private Float star;

    private LocalDate watchedAt;

    private ArticleTime watchedTime;

    private UUID userId;

    private String movieId;

    private String title;

    private String keywords;

    private String poster;
}
