package com.talkingPotatoes.potatoesProject.blog.repository;

import com.talkingPotatoes.potatoesProject.blog.dto.response.GetCommentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommentQueryRepository {

    Page<GetCommentResponse> findByArticleId(UUID articleId, Pageable pageable);
}
