package com.talkingPotatoes.potatoesProject.user.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class BaseDto {
	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
}
