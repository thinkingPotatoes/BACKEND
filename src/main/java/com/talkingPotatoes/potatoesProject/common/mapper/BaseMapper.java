package com.talkingPotatoes.potatoesProject.common.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.talkingPotatoes.potatoesProject.common.dto.BaseDto;
import com.talkingPotatoes.potatoesProject.common.entity.BaseEntity;

@Mapper(componentModel = "spring")
public interface BaseMapper {
	BaseMapper instance = Mappers.getMapper(BaseMapper.class);

	BaseEntity dtoToEntity(BaseDto baseDto);

	BaseDto entityToDto(BaseEntity baseEntity);
}
