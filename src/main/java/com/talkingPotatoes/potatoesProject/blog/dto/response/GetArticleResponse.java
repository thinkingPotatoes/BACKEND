package com.talkingPotatoes.potatoesProject.blog.dto.response;

import com.talkingPotatoes.potatoesProject.blog.entity.ArticleTime;
import com.talkingPotatoes.potatoesProject.blog.entity.Scope;
import com.talkingPotatoes.potatoesProject.movie.dto.MovieDto;
import com.talkingPotatoes.potatoesProject.user.dto.BlogUserDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class GetArticleResponse {
    private UUID id;

    private UUID userId;

    private BlogUserDto blogUserDto;

    private String movieId;

    private MovieDto movieDto;

    private String subject;

    private String content;

    private Float star;

    private Scope scope;

    private String theater;

    private String seat;

    private Boolean spoiler;

    private Long likeCnt;

    private Long commentCnt;

    private String poster;

    private LocalDate watchedAt;

    private ArticleTime watchedTime;

    private LocalDateTime createdAt;
}
