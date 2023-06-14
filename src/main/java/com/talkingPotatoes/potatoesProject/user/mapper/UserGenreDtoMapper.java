package com.talkingPotatoes.potatoesProject.user.mapper;

import java.util.List;
import java.util.UUID;

import org.mapstruct.Mapper;

import com.talkingPotatoes.potatoesProject.user.dto.UserGenreDto;

@Mapper(componentModel = "spring")
public interface UserGenreDtoMapper {
	UserGenreDto fromSignupRequest(UUID genreId);

	List<UserGenreDto> fromSignupRequest(List<UUID> genreList);
}
