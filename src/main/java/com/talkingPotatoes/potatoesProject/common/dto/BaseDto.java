package com.talkingPotatoes.potatoesProject.common.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class BaseDto {
	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
}
