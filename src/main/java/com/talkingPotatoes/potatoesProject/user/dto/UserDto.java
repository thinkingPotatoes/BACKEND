package com.talkingPotatoes.potatoesProject.user.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.talkingPotatoes.potatoesProject.user.entity.Platform;

import lombok.Data;

@Data
public class UserDto extends BaseDto {
	private UUID id;

	private String userId;

	private String password;

	private String nickname;

	private String title;

	private Platform platform;

	private LocalDateTime deletedAt;
}
