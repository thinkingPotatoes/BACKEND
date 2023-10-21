package com.talkingPotatoes.potatoesProject.blog.mapper;

import com.talkingPotatoes.potatoesProject.blog.dto.ArticleDto;
import com.talkingPotatoes.potatoesProject.blog.entity.Article;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArticleMapper {
    Article toEntity(ArticleDto articleDto);

    ArticleDto toDto(Article article);

    ArticleDto toDto(Article article, Long commentCnt);

    List<ArticleDto> toDto(List<Article> articles);

    ArticleDto toDto(Article article, String poster, Long commentCnt);

}
