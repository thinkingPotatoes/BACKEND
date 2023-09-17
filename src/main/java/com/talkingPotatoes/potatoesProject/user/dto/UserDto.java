package com.talkingPotatoes.potatoesProject.user.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.talkingPotatoes.potatoesProject.common.dto.BaseDto;
import com.talkingPotatoes.potatoesProject.user.entity.Platform;

import com.talkingPotatoes.potatoesProject.user.entity.Role;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class UserDto extends BaseDto {
	private UUID id;

	private String userId;

	private String password;

	private String nickname;

	private String title;

	private Platform platform;

	private Role role;

	private LocalDateTime deletedAt;
}
