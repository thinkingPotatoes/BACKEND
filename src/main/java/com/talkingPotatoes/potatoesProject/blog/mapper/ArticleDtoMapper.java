package com.talkingPotatoes.potatoesProject.blog.mapper;

import com.talkingPotatoes.potatoesProject.blog.dto.ArticleDto;
import com.talkingPotatoes.potatoesProject.blog.dto.request.CreateArticleRequest;
import com.talkingPotatoes.potatoesProject.blog.dto.request.UpdateArticleRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleDtoMapper {
    ArticleDto fromCreateArticleRequest(CreateArticleRequest createArticleRequest);
    ArticleDto fromUpdateArticleRequest(UpdateArticleRequest updateArticleRequest);
}
