package com.talkingPotatoes.potatoesProject.blog.service;

import com.talkingPotatoes.potatoesProject.blog.dto.CommentDto;
import com.talkingPotatoes.potatoesProject.blog.dto.request.CommentRequest;
import com.talkingPotatoes.potatoesProject.blog.dto.response.GetCommentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CommentService {

    void createComment(CommentDto commentDto);

    void updateComment(CommentRequest commentRequest, UUID id);

    void deleteComment(UUID userId, UUID id);

    Page<CommentDto> getCommentByArticleId(UUID articleId, Pageable pageable);

    void updateCommentLikes(UUID id, UUID commentId);
}
