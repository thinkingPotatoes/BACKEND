package com.talkingPotatoes.potatoesProject.movie.mapper;

import com.talkingPotatoes.potatoesProject.movie.dto.StaffDto;
import com.talkingPotatoes.potatoesProject.movie.entity.Staff;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StaffMapper {
    Staff toEntity(StaffDto staffDto);

    StaffDto toDto(Staff staff);

    List<Staff> toEntity(List<StaffDto> staffDtoList);

    List<StaffDto> toDto(List<Staff> staffList);
}
