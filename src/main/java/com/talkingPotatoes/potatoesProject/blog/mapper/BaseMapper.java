package com.talkingPotatoes.potatoesProject.blog.mapper;

import com.talkingPotatoes.potatoesProject.blog.dto.BaseDto;
import com.talkingPotatoes.potatoesProject.blog.entity.BaseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BaseMapper {
    BaseMapper instance = Mappers.getMapper(BaseMapper.class);

    BaseEntity dtoToEntity(BaseDto baseDto);

    BaseDto entityToDto(BaseEntity baseEntity);
}
