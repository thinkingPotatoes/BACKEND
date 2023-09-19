package com.talkingPotatoes.potatoesProject.blog.service.implement;

import com.talkingPotatoes.potatoesProject.blog.dto.CommentDto;
import com.talkingPotatoes.potatoesProject.blog.dto.response.GetCommentResponse;
import com.talkingPotatoes.potatoesProject.blog.entity.Comment;
import com.talkingPotatoes.potatoesProject.blog.mapper.CommentMapper;
import com.talkingPotatoes.potatoesProject.blog.repository.CommentQueryRepository;
import com.talkingPotatoes.potatoesProject.blog.repository.CommentRepository;
import com.talkingPotatoes.potatoesProject.blog.service.CommentService;
import com.talkingPotatoes.potatoesProject.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentQueryRepository commentQueryRepository;

    private final CommentMapper commentMapper;
    @Override
    @Transactional
    public void createComment(CommentDto commentDto) {
        Comment comment = commentRepository.save(commentMapper.toEntity(commentDto));
    }

    @Override
    @Transactional
    public void updateComment(CommentDto commentDto) {
        /* 댓글 정상 조회 */
        if (!commentRepository.existsById(commentDto.getId())) {
            throw new NotFoundException("댓글이 존재하지 않습니다.");
        } else {
            Comment comment = commentRepository.save(commentMapper.toEntity(commentDto));
        }
    }

    @Override
    @Transactional
    public void deleteComment(UUID userId, UUID id) {
        if (!commentRepository.existsByUserIdAndId(userId, id)) {
            throw new NotFoundException("댓글이 존재하지 않습니다.");
        } else {
            Comment comment = commentRepository.getReferenceById(id);
            commentRepository.delete(comment);
        }
    }

    @Override
    public Page<GetCommentResponse> getCommentByArticleId(UUID articleId, Pageable pageable) {
        Page<GetCommentResponse> comments = commentQueryRepository.findByArticleId(articleId, pageable);
        return new PageImpl<>(comments.getContent(), comments.getPageable(), comments.getTotalElements());
    }
}
