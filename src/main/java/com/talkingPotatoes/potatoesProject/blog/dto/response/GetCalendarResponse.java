package com.talkingPotatoes.potatoesProject.blog.dto.response;

import com.talkingPotatoes.potatoesProject.blog.entity.ArticleTime;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class GetCalendarResponse {
    private UUID id;

    private UUID userId;

    private String movieId;

    private String poster;

    private LocalDate watchedAt;

    private ArticleTime watchedTime;
}
