package com.talkingPotatoes.potatoesProject.movie.mapper;

import com.talkingPotatoes.potatoesProject.movie.dto.ActorDto;
import com.talkingPotatoes.potatoesProject.movie.entity.Actor;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActorMapper {
    Actor toEntity(ActorDto staffDto);

    ActorDto toDto(Actor staff);

    List<Actor> toEntity(List<ActorDto> staffDtoList);

    List<ActorDto> toDto(List<Actor> staffList);
}
