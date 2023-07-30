package com.talkingPotatoes.potatoesProject.movie.repository.implement;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.talkingPotatoes.potatoesProject.movie.entity.Movie;
import com.talkingPotatoes.potatoesProject.movie.repository.MovieQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.talkingPotatoes.potatoesProject.movie.entity.QMovie.movie;
import static com.talkingPotatoes.potatoesProject.movie.entity.QStaff.staff;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MovieQueryRepositoryImpl implements MovieQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Movie> findByKeyword(String keyword, Pageable pageable) {
        JPAQuery<Movie> query = queryFactory.selectFrom(movie)
                .from(movie)
                .where(movie.title.contains(keyword).or(
                        movie.docId.eq(
                                queryFactory.select(staff.docId)
                                .from(staff)
                                .where(staff.docId.eq(movie.docId))
                                .where(staff.staffNm.eq(keyword))
                        )
                ))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(movieSort(pageable));

        List<Movie> movies = query.fetch();

        JPAQuery<Long> countQuery = queryFactory.select(movie.count())
                .from(movie)
                .where(movie.title.contains(keyword).or(
                        movie.docId.eq(
                                queryFactory.select(staff.docId)
                                        .from(staff)
                                        .where(staff.docId.eq(movie.docId))
                                        .where(staff.staffNm.eq(keyword))
                        )
                ));

        return PageableExecutionUtils.getPage(movies, pageable, countQuery::fetchOne);
    }

    /**
     * OrderSpecifier 를 쿼리로 반환하여 정렬조건을 맞춰준다.
     * 리스트 정렬
     * @param page
     * @return
     */
    private OrderSpecifier<?> movieSort(Pageable page) {
        //서비스에서 보내준 Pageable 객체에 정렬조건 null 값 체크
        if (!page.getSort().isEmpty()) {
            //정렬값이 들어 있으면 for 사용하여 값을 가져온다
            for (Sort.Order order : page.getSort()) {
                // 서비스에서 넣어준 DESC or ASC 를 가져온다.
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                // 서비스에서 넣어준 정렬 조건을 스위치 케이스 문을 활용하여 셋팅하여 준다.
                switch (order.getProperty()){
                    case "repRlsDate":
                        return new OrderSpecifier(direction, movie.repRlsDate);
                }
            }
        }
        return null;
    }
}
