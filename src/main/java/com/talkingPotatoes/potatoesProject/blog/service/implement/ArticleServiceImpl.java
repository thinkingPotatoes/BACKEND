package com.talkingPotatoes.potatoesProject.blog.service.implement;

import com.talkingPotatoes.potatoesProject.blog.dto.ArticleDto;
import com.talkingPotatoes.potatoesProject.blog.dto.ArticleSearchDto;
import com.talkingPotatoes.potatoesProject.blog.entity.Article;
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
        log.info("ArticleServiceImpl::: createArticle start");
        Article article = articleRepository.save(articleMapper.toEntity(articleDto));
        log.info("ArticleServiceImpl::: finish ", String.valueOf(article.getId()));
    }

    @Override
    @Transactional
    public void updateArticle(ArticleDto articleDto) {
        log.info("ArticleServiceImpl::: updateArticle start");

        /* 리뷰 상태 정상 조회 */
        if (!articleRepository.existsByUserIdAndId(articleDto.getUserId(), articleDto.getId())) {
            log.info("ArticleServiceImpl::: updateArticle Blog is NULL");
            throw new NotFoundException("글이 존재하지 않습니다.");
        } else {
            Article article = articleRepository.save(articleMapper.toEntity(articleDto));
            log.info("ArticleServiceImpl::: finish ", String.valueOf(article.getId()));
        }

    }

    @Override
    @Transactional
    public void deleteArticle(UUID userId, UUID id) {
        log.info("ArticleServiceImpl::: deleteArticle start ID: ");

        if (!articleRepository.existsByUserIdAndId(userId, id)) {
            log.info("ArticleServiceImpl::: deleteArticle Blog is NULL");
            throw new NotFoundException("글이 존재하지 않습니다.");
        } else {
            Article article = articleRepository.getReferenceById(id);
            articleRepository.delete(article);
            log.info("ArticleServiceImpl::: deleteArticle finish ");
        }
    }

    @Override
    public ArticleDto searchArticleById(UUID id) {
        log.info("searchArticleById ::: id: {}", id);
        Article article = articleRepository.getReferenceById(id);
        return articleMapper.toDto(article);
    }

    @Override
    public Page<ArticleDto> searchArticleByMovieId(String movieId, Pageable pageable) {
        log.info("searchArticleByMovieId ::: id: {}", movieId);
        Page<Article> articles = articleRepository.findAllByMovieId(movieId, pageable);
        return new PageImpl<>(articleMapper.toDto(articles.getContent()), articles.getPageable(), articles.getTotalElements());

    }

    @Override
    public Page<ArticleDto> searchArticleByUserId(UUID userId, Pageable pageable) {
        log.info("searchArticleByUserId ::: id: {}", userId);
        Page<Article> articles = articleRepository.findAllByUserId(userId, pageable);
        return new PageImpl<>(articleMapper.toDto(articles.getContent()), articles.getPageable(), articles.getTotalElements());
    }

    @Override
    public Page<ArticleSearchDto> searchArticleByUserIdAndKeyword(UUID userId, String keyword, Pageable pageable) {
        log.info("ArticleServiceImpl::: searchArticleByUserIdAndKeyword id: {}, keyword: {}", userId, keyword);
        Page<Article> articles = articleQueryRepository.findByUserIdAndKeyword(userId, keyword, pageable);

        List<ArticleSearchDto> articleSearchDtoList = new ArrayList<>();
        for (Article article : articles.getContent()) {
            String poster = movieRepository.findById(article.getMovieId()).orElseThrow(() -> new NotFoundException("영화를 찾을 수 없습니다.")).getPoster();
            Long likesCnt = likesRepository.countByArticleId(article.getId());
            Long commentCnt = commentRepository.countByArticleId(article.getId());

            articleSearchDtoList.add(articleMapper.toDto(article, poster, likesCnt, commentCnt));
        }

        log.info("ArticleServiceImpl::: searchArticleByUserIdAndKeyword count: {}", articles.getNumberOfElements());
        return new PageImpl<>(articleSearchDtoList, articles.getPageable(), articles.getTotalElements());
    }
}
