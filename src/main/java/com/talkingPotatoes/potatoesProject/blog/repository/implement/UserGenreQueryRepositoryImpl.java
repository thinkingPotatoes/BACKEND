package com.talkingPotatoes.potatoesProject.blog.repository.implement;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.talkingPotatoes.potatoesProject.blog.entity.Genre;
import com.talkingPotatoes.potatoesProject.blog.entity.UserGenre;
import com.talkingPotatoes.potatoesProject.blog.repository.UserGenreQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.talkingPotatoes.potatoesProject.blog.entity.QGenre.genre1;
import static com.talkingPotatoes.potatoesProject.blog.entity.QUserGenre.userGenre;

@Repository
@RequiredArgsConstructor
public class UserGenreQueryRepositoryImpl implements UserGenreQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<String> findByUserId(UUID userId) {
        JPAQuery<String> query = queryFactory.select(genre1.genre).from(genre1)
                .innerJoin(userGenre)
                .on(genre1.id.eq(userGenre.genreId))
                .where(userGenre.userId.eq(userId));

        return query.fetch();
    }
}
