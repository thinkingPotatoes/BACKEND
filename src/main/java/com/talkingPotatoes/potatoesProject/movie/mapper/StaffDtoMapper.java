package com.talkingPotatoes.potatoesProject.movie.mapper;

import com.talkingPotatoes.potatoesProject.movie.dto.StaffDto;
import com.talkingPotatoes.potatoesProject.movie.dto.response.StaffResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StaffDtoMapper {
    StaffResponse toResponse(StaffDto dto);

    List<StaffResponse> toResponse(List<StaffDto> dto);
}
