package com.talkingPotatoes.potatoesProject.user.mapper;

import com.talkingPotatoes.potatoesProject.user.dto.BlogUserDto;
import org.mapstruct.Mapper;

import com.talkingPotatoes.potatoesProject.user.dto.UserDto;
import com.talkingPotatoes.potatoesProject.user.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	User toEntity(UserDto userDto);

	UserDto toDto(User user);

    BlogUserDto toBlogUserDto(User user);
}
