package com.talkingPotatoes.potatoesProject.blog.mapper;

import com.talkingPotatoes.potatoesProject.blog.dto.BlogDto;
import com.talkingPotatoes.potatoesProject.blog.dto.request.UpdateBlogRequest;
import com.talkingPotatoes.potatoesProject.blog.dto.response.BlogResponse;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface BlogDtoMapper {
    BlogDto fromBlogRequest(UUID userId, UpdateBlogRequest updateBlogRequest);

    BlogResponse toResponse(BlogDto blogDto);
}
