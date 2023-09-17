package com.talkingPotatoes.potatoesProject.blog.repository.implement;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.talkingPotatoes.potatoesProject.blog.dto.CalendarDto;
import com.talkingPotatoes.potatoesProject.blog.entity.Article;
import com.talkingPotatoes.potatoesProject.blog.repository.ArticleQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static com.talkingPotatoes.potatoesProject.blog.entity.QArticle.article;
import static com.talkingPotatoes.potatoesProject.movie.entity.QMovie.movie;

@Repository
@RequiredArgsConstructor
public class ArticleQueryRepositoryImpl implements ArticleQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Article> findByUserIdAndKeyword(UUID userId, String keyword, Pageable pageable) {
        JPAQuery<Article> query = queryFactory.selectFrom(article)
                .from(article)
                .where(article.userId.eq(userId))
                .where(article.subject.contains(keyword)
                        .or(article.content.contains(keyword))
                        .or(article.movieId.in(queryFactory.select(movie.docId).from(movie).where(movie.title.contains(keyword)))))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(articleSort(pageable));

        List<Article> articles = query.fetch();

        JPAQuery<Long> countQuery = queryFactory.select(article.count())
                .from(article)
                .where(article.userId.eq(userId))
                .where(article.subject.contains(keyword)
                        .or(article.content.contains(keyword))
                        .or(article.movieId.in(queryFactory.select(movie.docId).from(movie).where(movie.title.contains(keyword)))));

        return PageableExecutionUtils.getPage(articles, pageable, countQuery::fetchOne);
    }

    @Override
    public List<CalendarDto> findByBetweenDate(UUID userId, LocalDate firstDate, LocalDate lastDate) {
        List<CalendarDto> dto = queryFactory.select(Projections.bean(CalendarDto.class,
                article.id, article.subject, article.star,
                article.watchedAt, article.userId, article.movieId,
                movie.title, movie.keywords, movie.poster))
                .from(article)
                .join(movie)
                .on(article.movieId.eq(movie.docId))
                .where(article.userId.eq(userId))
                .where(article.watchedAt.between(firstDate, lastDate))
                .fetch();

        return dto;
    }

    @Override
    public List<CalendarDto> findByDate(UUID userId, LocalDate date) {
        List<CalendarDto> dto = queryFactory.select(Projections.bean(CalendarDto.class,
                article.id, article.subject, article.star,
                article.watchedAt, article.userId, article.movieId,
                movie.title, movie.keywords, movie.poster))
                .from(article)
                .join(movie)
                .on(article.movieId.eq(movie.docId))
                .where(article.userId.eq(userId))
                .where(article.watchedAt.eq(date))
                .fetch();

        return dto;
    }

    /**
     * OrderSpecifier 를 쿼리로 반환하여 정렬조건을 맞춰준다.
     * 리스트 정렬
     *
     * @param page
     * @return
     */
    private OrderSpecifier<?> articleSort(Pageable page) {
        //서비스에서 보내준 Pageable 객체에 정렬조건 null 값 체크
        if (!page.getSort().isEmpty()) {
            //정렬값이 들어 있으면 for 사용하여 값을 가져온다
            for (Sort.Order order : page.getSort()) {
                // 서비스에서 넣어준 DESC or ASC 를 가져온다.
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                // 서비스에서 넣어준 정렬 조건을 스위치 케이스 문을 활용하여 셋팅하여 준다.
                switch (order.getProperty()) {
                    case "createdAt":
                        return new OrderSpecifier(direction, article.createdAt);
                    case "watchedAt":
                        return new OrderSpecifier(direction, article.watchedAt);
                    case "likeCnt":
                        return new OrderSpecifier(direction, article.likeCnt);
                }
            }
        }
        return null;
    }
}
