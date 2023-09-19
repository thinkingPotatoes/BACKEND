package com.talkingPotatoes.potatoesProject.blog.mapper;

import com.talkingPotatoes.potatoesProject.blog.dto.CommentDto;
import com.talkingPotatoes.potatoesProject.blog.dto.request.CreateCommentRequest;
import com.talkingPotatoes.potatoesProject.blog.dto.request.UpdateCommentRequest;
import com.talkingPotatoes.potatoesProject.blog.dto.response.GetCommentResponse;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.UUID;
@Mapper(componentModel = "spring")
public interface CommentDtoMapper {
    CommentDto fromCreateCommentRequest(UUID userId, CreateCommentRequest createCommentRequest);

    CommentDto fromUpdateCommentRequest(UUID userId, UpdateCommentRequest updateCommentRequest);

    List<GetCommentResponse> toGetCommentResponse(List<CommentDto> commentDtoList);
}
