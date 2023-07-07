package com.talkingPotatoes.potatoesProject.movie.mapper;

import com.talkingPotatoes.potatoesProject.movie.dto.DirectorDto;
import com.talkingPotatoes.potatoesProject.movie.entity.Director;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DirectorMapper {
    Director toEntity(DirectorDto directorDto);

    DirectorDto toDto(Director director);

    List<Director> toEntity(List<DirectorDto> directorDtoList);

    List<DirectorDto> toDto(List<Director> directorList);
}
