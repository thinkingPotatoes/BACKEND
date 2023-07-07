package com.talkingPotatoes.potatoesProject.movie.mapper;

import com.talkingPotatoes.potatoesProject.movie.dto.ActorDto;
import com.talkingPotatoes.potatoesProject.movie.entity.Actor;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActorMapper {
    Actor toEntity(ActorDto actorDto);

    ActorDto toDto(Actor actor);

    List<Actor> toEntity(List<ActorDto> actorDtoList);

    List<ActorDto> toDto(List<Actor> actorList);
}
