package com.talkingPotatoes.potatoesProject.blog.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class GetCalendarResponse {
    private UUID id;

    private UUID userId;

    private String movieId;

    private String poster;

    private LocalDateTime watchedAt;
}
