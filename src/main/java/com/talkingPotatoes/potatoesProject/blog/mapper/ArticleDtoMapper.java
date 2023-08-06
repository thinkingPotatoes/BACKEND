package com.talkingPotatoes.potatoesProject.blog.mapper;

import com.talkingPotatoes.potatoesProject.blog.dto.ArticleDto;
import com.talkingPotatoes.potatoesProject.blog.dto.request.CreateArticleRequest;
import com.talkingPotatoes.potatoesProject.blog.dto.request.UpdateArticleRequest;
import com.talkingPotatoes.potatoesProject.blog.dto.ArticleSearchDto;
import com.talkingPotatoes.potatoesProject.blog.dto.response.GetArticleResponse;
import com.talkingPotatoes.potatoesProject.blog.dto.response.SearchArticleResponse;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ArticleDtoMapper {
    ArticleDto fromCreateArticleRequest(UUID userId, CreateArticleRequest createArticleRequest);

    ArticleDto fromUpdateArticleRequest(UUID userId, UpdateArticleRequest updateArticleRequest);

    SearchArticleResponse toSearchMyArticleResponse(ArticleSearchDto articleSearchDto);

    List<SearchArticleResponse> toSearchMyArticleResponse(List<ArticleSearchDto> articleSearchDto);

    GetArticleResponse toGetArticleResponse(ArticleDto articleDto);

    List<GetArticleResponse> toGetArticleResponse(List<ArticleDto> articleDtoList);
}
