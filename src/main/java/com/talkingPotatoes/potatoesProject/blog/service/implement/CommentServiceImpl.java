package com.talkingPotatoes.potatoesProject.blog.service.implement;

import com.talkingPotatoes.potatoesProject.blog.dto.CommentDto;
import com.talkingPotatoes.potatoesProject.blog.dto.request.CommentRequest;
import com.talkingPotatoes.potatoesProject.blog.dto.response.GetCommentResponse;
import com.talkingPotatoes.potatoesProject.blog.entity.Article;
import com.talkingPotatoes.potatoesProject.blog.entity.Comment;
import com.talkingPotatoes.potatoesProject.blog.entity.CommentLikes;
import com.talkingPotatoes.potatoesProject.blog.entity.Likes;
import com.talkingPotatoes.potatoesProject.blog.mapper.CommentDtoMapper;
import com.talkingPotatoes.potatoesProject.blog.mapper.CommentMapper;
import com.talkingPotatoes.potatoesProject.blog.repository.CommentLikesRepository;
import com.talkingPotatoes.potatoesProject.blog.repository.CommentQueryRepository;
import com.talkingPotatoes.potatoesProject.blog.repository.CommentRepository;
import com.talkingPotatoes.potatoesProject.blog.service.CommentService;
import com.talkingPotatoes.potatoesProject.common.exception.NotFoundException;
import com.talkingPotatoes.potatoesProject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentQueryRepository commentQueryRepository;
    private final UserRepository userRepository;
    private final CommentLikesRepository commentLikesRepository;

    private final CommentMapper commentMapper;
    private final CommentDtoMapper commentDtoMapper;

    @Override
    @Transactional
    public void createComment(CommentDto commentDto) {
        Comment comment = commentRepository.save(commentMapper.toEntity(commentDto));
        if (commentDto.getParentId() != null) {
            comment.updateParent(commentRepository.findById(commentDto.getParentId()).orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다.")));
        }
    }

    @Override
    @Transactional
    public void updateComment(CommentRequest commentRequest, UUID id) {
        /* 댓글 정상 조회 */
        if (!commentRepository.existsById(id)) {
            throw new NotFoundException("댓글이 존재하지 않습니다.");
        } else {
            // Comment comment = commentRepository.save(commentMapper.toEntity(commentDto));
            Comment comment = commentRepository.getReferenceById(id);
            Comment updateComment = Comment.builder()
                    .id(id)
                    .parent(comment.getParent())
                    .children(comment.getChildren())
                    .articleId(comment.getArticleId())
                    .userId(comment.getUserId())
                    .content(commentRequest.getContent())
                    .likeCnt(comment.getLikeCnt())
                    .updatedAt(LocalDateTime.now())
                    .build();
            commentRepository.save(updateComment);
        }
    }

    @Override
    @Transactional
    public void deleteComment(UUID userId, UUID id) {
        if (!commentRepository.existsByUserIdAndId(userId, id)) {
            throw new NotFoundException("댓글이 존재하지 않습니다.");
        } else {
            Comment comment = commentRepository.getReferenceById(id);
            comment.updateDeletedAt();
        }
    }

    @Override
    public Page<CommentDto> getCommentByArticleId(UUID articleId, Pageable pageable) {
        Page<Comment> comments = commentQueryRepository.findByArticleId(articleId, pageable);

        List<CommentDto> commentDtoList = new ArrayList<>();
        for (Comment comment : comments.getContent()) {
            String nickname = userRepository.findById(comment.getUserId()).get().getNickname();
            commentDtoList.add(commentMapper.toDto(comment, nickname));
        }
        return new PageImpl<>(commentDtoList, comments.getPageable(), comments.getTotalElements());
    }

    @Override
    @Transactional
    public void updateCommentLikes(UUID userId, UUID commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("댓글을 찾을 수가 없습니다."));

        boolean clicked = true;

        if (commentLikesRepository.existsByUserIdAndCommentIdAndClickedIsTrue(userId, commentId)) {
            clicked = false;
            comment.updateCommentLikeCnt(-1);
        } else {
            comment.updateCommentLikeCnt(1);
        }

        commentLikesRepository.save(CommentLikes.builder()
                .userId(userId)
                .commentId(commentId)
                .clicked(clicked)
                .build());

        commentRepository.save(comment);
    }
}
