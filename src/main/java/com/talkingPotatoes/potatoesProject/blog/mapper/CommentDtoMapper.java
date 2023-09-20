package com.talkingPotatoes.potatoesProject.blog.mapper;

import com.talkingPotatoes.potatoesProject.blog.dto.CommentDto;
import com.talkingPotatoes.potatoesProject.blog.dto.request.CommentRequest;
import com.talkingPotatoes.potatoesProject.blog.dto.response.GetCommentResponse;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.UUID;
@Mapper(componentModel = "spring")
public interface CommentDtoMapper {
    CommentDto fromCreateCommentRequest(UUID userId, UUID articleId, CommentRequest createCommentRequest);

    CommentDto fromUpdateCommentRequest(UUID userId, UUID id, UUID articleId,CommentRequest commentRequest);

    List<GetCommentResponse> toGetCommentResponse(List<CommentDto> commentDtoList);
}
