package com.talkingPotatoes.potatoesProject.blog.mapper;

import com.talkingPotatoes.potatoesProject.blog.dto.ArticleDto;
import com.talkingPotatoes.potatoesProject.blog.dto.CalendarDto;
import com.talkingPotatoes.potatoesProject.blog.dto.request.CreateArticleRequest;
import com.talkingPotatoes.potatoesProject.blog.dto.request.UpdateArticleRequest;
import com.talkingPotatoes.potatoesProject.blog.dto.response.GetArticleResponse;
import com.talkingPotatoes.potatoesProject.blog.dto.response.GetCalendarDayResponse;
import com.talkingPotatoes.potatoesProject.blog.dto.response.GetCalendarResponse;
import com.talkingPotatoes.potatoesProject.blog.dto.response.SearchArticleResponse;
import com.talkingPotatoes.potatoesProject.movie.dto.MovieDto;
import com.talkingPotatoes.potatoesProject.user.dto.BlogUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ArticleDtoMapper {
    ArticleDto fromCreateArticleRequest(UUID userId, CreateArticleRequest createArticleRequest);

    ArticleDto fromUpdateArticleRequest(UUID userId, UpdateArticleRequest updateArticleRequest);

    SearchArticleResponse toSearchMyArticleResponse(ArticleDto articleSearchDto);

    List<SearchArticleResponse> toSearchMyArticleResponse(List<ArticleDto> articleSearchDto);

    @Mapping(source = "movieDto.poster", target = "poster")
    @Mapping(source = "movieDto", target = "movieDto")
    GetArticleResponse toGetArticleResponse(ArticleDto articleDto, BlogUserDto blogUserDto, MovieDto movieDto);

    List<GetArticleResponse> toGetArticleResponse(List<ArticleDto> articleDtoList);

    GetCalendarResponse toGetCalendarResponse(CalendarDto calendarDto);

    List<GetCalendarResponse> toGetCalendarResponse(List<CalendarDto> calendarDtoList);

    GetCalendarDayResponse toGetCalendarDayResponse(CalendarDto calendarDto);

    List<GetCalendarDayResponse> toGetCalendarDayResponse(List<CalendarDto> calendarDtoList);
}
