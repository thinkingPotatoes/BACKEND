package com.talkingPotatoes.potatoesProject.user.mapper;

import com.talkingPotatoes.potatoesProject.user.dto.SimUserDto;
import com.talkingPotatoes.potatoesProject.user.entity.SimUser;
import com.talkingPotatoes.potatoesProject.user.repository.SimUserRepository;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SimUserMapper {
    SimUser toEntity(SimUserDto simUserDto);

    SimUserDto toDto(SimUser simUser);

    List<SimUser> toEntity(List<SimUserDto> simUserDtoList);

    List<SimUserDto> toDto(List<SimUser> simUserList);
}
