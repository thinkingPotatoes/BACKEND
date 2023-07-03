package com.talkingPotatoes.potatoesProject.blog.service.implement;

import com.talkingPotatoes.potatoesProject.blog.dto.ArticleDto;
import com.talkingPotatoes.potatoesProject.blog.entity.Article;
import com.talkingPotatoes.potatoesProject.blog.mapper.ArticleMapper;
import com.talkingPotatoes.potatoesProject.blog.repository.ArticleRepository;
import com.talkingPotatoes.potatoesProject.blog.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    @Override
    public boolean existArticleById(UUID id){
        if (articleRepository.existsById(id)){
            return true;
        } else {
            return false;
        }
    }

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
        if(!articleRepository.existsById(articleDto.getId())){
            log.info("ArticleServiceImpl::: updateArticle Blog is NULL");
        }else{
            Article article = articleRepository.save(articleMapper.toEntity(articleDto));
            log.info("ArticleServiceImpl::: finish ", String.valueOf(article.getId()));
        }

    }

    @Override
    @Transactional
    public void deleteArticle(UUID id){
        log.info("ArticleServiceImpl::: deleteArticle start ID: ");

        if(!articleRepository.existsById(id)){
            log.info("ArticleServiceImpl::: deleteArticle Blog is NULL");
        }else{
            Article article = articleRepository.getReferenceById(id);
            articleRepository.delete(article);
            log.info("ArticleServiceImpl::: deleteArticle finish ");
        }
    }

    @Override
    public ArticleDto searchArticleById(UUID id){
        log.info("searchArticleById ::: id: {}", id);
        Article article = articleRepository.getReferenceById(id);
        return articleMapper.toDto(article);
    }

    @Override
    public List<ArticleDto> searchArticleByMovieId(UUID movieId){
        log.info("searchArticleById ::: id: {}", movieId);
        List<Article> articleList = articleRepository.findAllByMovieId(movieId);
        return articleList.stream().map(m -> articleMapper.toDto(m))
                .collect(Collectors.toList());

    }
}
