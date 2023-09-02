package com.talkingPotatoes.potatoesProject.blog.mapper;

import com.talkingPotatoes.potatoesProject.blog.dto.ArticleDto;
import com.talkingPotatoes.potatoesProject.blog.dto.ArticleSearchDto;
import com.talkingPotatoes.potatoesProject.blog.entity.Article;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArticleMapper {
    Article toEntity(ArticleDto articleDto);

    ArticleDto toDto(Article article);

    List<ArticleDto> toDto(List<Article> articles);

    ArticleSearchDto toDto(Article article, String poster, Long likesCnt);
}
