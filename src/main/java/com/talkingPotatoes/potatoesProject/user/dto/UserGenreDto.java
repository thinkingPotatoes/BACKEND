package com.talkingPotatoes.potatoesProject.user.dto;

import java.util.UUID;

import com.talkingPotatoes.potatoesProject.common.dto.BaseDto;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class UserGenreDto extends BaseDto {
	private UUID id;

	private UUID userId;

	private UUID genreId;
}
