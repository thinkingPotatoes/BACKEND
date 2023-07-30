package com.talkingPotatoes.potatoesProject.blog.mapper;

import com.talkingPotatoes.potatoesProject.blog.dto.ArticleDto;
import com.talkingPotatoes.potatoesProject.blog.dto.request.CreateArticleRequest;
import com.talkingPotatoes.potatoesProject.blog.dto.request.UpdateArticleRequest;
import com.talkingPotatoes.potatoesProject.blog.dto.ArticleSearchDto;
import com.talkingPotatoes.potatoesProject.blog.dto.response.SearchArticleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ArticleDtoMapper {
    ArticleDto fromCreateArticleRequest(UUID userId, CreateArticleRequest createArticleRequest);
    ArticleDto fromUpdateArticleRequest(UUID userId,UpdateArticleRequest updateArticleRequest);
    SearchArticleResponse toSearchMyArticleResponse(List<ArticleSearchDto> articleSearchDtoList, long totalCnt, int curPage);
}
