package com.talkingPotatoes.potatoesProject.blog.mapper;

import com.talkingPotatoes.potatoesProject.blog.dto.CommentDto;
import com.talkingPotatoes.potatoesProject.blog.dto.request.CreateCommentRequest;
import com.talkingPotatoes.potatoesProject.blog.dto.request.UpdateCommentRequest;
import org.mapstruct.Mapper;

import java.util.UUID;
@Mapper(componentModel = "spring")
public interface CommentDtoMapper {
    CommentDto fromCreateCommentRequest(UUID userId, CreateCommentRequest createCommentRequest);

    CommentDto fromUpdateCommentRequest(UUID userId, UpdateCommentRequest updateCommentRequest);
}
