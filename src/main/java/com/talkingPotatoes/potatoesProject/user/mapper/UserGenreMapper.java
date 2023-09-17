package com.talkingPotatoes.potatoesProject.user.mapper;

import java.util.List;

import com.talkingPotatoes.potatoesProject.user.dto.UserGenreDto;
import org.mapstruct.Mapper;

import com.talkingPotatoes.potatoesProject.user.entity.UserGenre;

@Mapper(componentModel = "spring")
public interface UserGenreMapper {
	UserGenre toEntity(UserGenreDto userGenreDto);

	UserGenreDto toDto(UserGenre user);

	List<UserGenre> toEntity(List<UserGenreDto> userGenreDtoList);

	List<UserGenreDto> toDto(List<UserGenre> userGenreList);
}