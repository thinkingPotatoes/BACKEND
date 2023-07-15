package com.talkingPotatoes.potatoesProject.blog.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class BaseDto {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
