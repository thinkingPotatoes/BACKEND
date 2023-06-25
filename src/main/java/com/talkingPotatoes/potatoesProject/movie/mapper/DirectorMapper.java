package com.talkingPotatoes.potatoesProject.movie.mapper;

import com.talkingPotatoes.potatoesProject.movie.dto.DirectorDto;
import com.talkingPotatoes.potatoesProject.movie.entity.Director;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DirectorMapper {
    Director toEntity(DirectorDto staffDto);

    DirectorDto toDto(Director staff);

    List<Director> toEntity(List<DirectorDto> staffDtoList);

    List<DirectorDto> toDto(List<Director> staffList);
}
