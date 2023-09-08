package com.talkingPotatoes.potatoesProject.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalendarDto {
    private UUID id;

    private String subject;

    private Float star;

    private LocalDateTime watchedAt;

    private UUID userId;

    private String movieId;

    private String title;

    private String keywords;

    private String poster;
}
