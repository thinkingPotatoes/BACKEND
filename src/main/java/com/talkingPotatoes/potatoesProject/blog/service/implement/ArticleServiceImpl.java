package com.talkingPotatoes.potatoesProject.blog.service.implement;

import com.talkingPotatoes.potatoesProject.blog.dto.ArticleDto;
import com.talkingPotatoes.potatoesProject.blog.dto.ArticleSearchDto;
import com.talkingPotatoes.potatoesProject.blog.entity.Article;
import com.talkingPotatoes.potatoesProject.blog.entity.Likes;
import com.talkingPotatoes.potatoesProject.blog.mapper.ArticleMapper;
import com.talkingPotatoes.potatoesProject.blog.repository.ArticleQueryRepository;
import com.talkingPotatoes.potatoesProject.blog.repository.ArticleRepository;
import com.talkingPotatoes.potatoesProject.blog.repository.CommentRepository;
import com.talkingPotatoes.potatoesProject.blog.repository.LikesRepository;
import com.talkingPotatoes.potatoesProject.blog.service.ArticleService;
import com.talkingPotatoes.potatoesProject.common.exception.NotFoundException;
import com.talkingPotatoes.potatoesProject.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleQueryRepository articleQueryRepository;
    private final CommentRepository commentRepository;
    private final LikesRepository likesRepository;
    private final MovieRepository movieRepository;

    private final ArticleMapper articleMapper;

    @Override
    @Transactional
    public void createArticle(ArticleDto articleDto) {
        Article article = articleRepository.save(articleMapper.toEntity(articleDto));
    }

    @Override
    @Transactional
    public void updateArticle(ArticleDto articleDto) {
        /* 리뷰 상태 정상 조회 */
        if (!articleRepository.existsByUserIdAndId(articleDto.getUserId(), articleDto.getId())) {
            throw new NotFoundException("글이 존재하지 않습니다.");
        } else {
            Article article = articleRepository.save(articleMapper.toEntity(articleDto));
        }

    }

    @Override
    @Transactional
    public void deleteArticle(UUID userId, UUID id) {
        if (!articleRepository.existsByUserIdAndId(userId, id)) {
            throw new NotFoundException("글이 존재하지 않습니다.");
        } else {
            Article article = articleRepository.getReferenceById(id);
            articleRepository.delete(article);
        }
    }

    @Override
    public ArticleDto searchArticleById(UUID id) {
        Article article = articleRepository.getReferenceById(id);
        return articleMapper.toDto(article);
    }

    @Override
    @Transactional
    public void updateLikes(UUID userId, UUID articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new NotFoundException("글을 찾을 수가 없습니다."));

        boolean clicked = true;

        if (likesRepository.existsByUserIdAndArticleIdAndClickedIsTrue(userId, articleId)) {
            clicked = false;
            article.updateLikeCnt(-1);
        } else {
            article.updateLikeCnt(1);
        }

        System.out.println(clicked);
        likesRepository.save(Likes.builder()
                .userId(userId)
                .articleId(articleId)
                .clicked(clicked)
                .build());

        articleRepository.save(article);
    }

    @Override
    public Page<ArticleDto> searchArticleByMovieId(String movieId, Pageable pageable) {
        Page<Article> articles = articleRepository.findAllByMovieId(movieId, pageable);
        return new PageImpl<>(articleMapper.toDto(articles.getContent()), articles.getPageable(), articles.getTotalElements());
    }

    @Override
    public Page<ArticleDto> searchArticleByUserId(UUID userId, Pageable pageable) {
        Page<Article> articles = articleRepository.findAllByUserId(userId, pageable);
        return new PageImpl<>(articleMapper.toDto(articles.getContent()), articles.getPageable(), articles.getTotalElements());
    }

    @Override
    public Page<ArticleSearchDto> searchArticleByUserIdAndKeyword(UUID userId, String keyword, Pageable pageable) {
        Page<Article> articles = articleQueryRepository.findByUserIdAndKeyword(userId, keyword, pageable);

        List<ArticleSearchDto> articleSearchDtoList = new ArrayList<>();
        for (Article article : articles.getContent()) {
            String poster = movieRepository.findById(article.getMovieId()).orElseThrow(() -> new NotFoundException("영화를 찾을 수 없습니다.")).getPoster();
            Long commentCnt = commentRepository.countByArticleId(article.getId());

            articleSearchDtoList.add(articleMapper.toDto(article, poster, commentCnt));
        }

        return new PageImpl<>(articleSearchDtoList, articles.getPageable(), articles.getTotalElements());
    }
}
