package com.talkingPotatoes.potatoesProject.blog.repository.implement;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.talkingPotatoes.potatoesProject.blog.entity.Comment;
import com.talkingPotatoes.potatoesProject.blog.repository.CommentQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.talkingPotatoes.potatoesProject.blog.entity.QComment.comment;

@Repository
@RequiredArgsConstructor
public class CommentQueryRepositoryImpl implements CommentQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Comment> findByArticleId(UUID articleId, Pageable pageable) {
        JPAQuery<Comment> query = queryFactory.selectFrom(comment)
                .where(comment.articleId.eq(articleId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(commentSort(pageable));

        List<Comment> comments = query.fetch();

        JPAQuery<Long> countQuery = queryFactory.select(comment.count())
                .from(comment)
                .where(comment.articleId.eq(articleId));

        return PageableExecutionUtils.getPage(comments, pageable, countQuery::fetchOne);
    }

    private OrderSpecifier<?> commentSort(Pageable pageable) {
        //서비스에서 보내준 Pageable 객체에 정렬조건 null 값 체크
        if (!pageable.getSort().isEmpty()) {
            //정렬값이 들어 있으면 for 사용하여 값을 가져온다
            for (Sort.Order order : pageable.getSort()) {
                // 서비스에서 넣어준 DESC or ASC 를 가져온다.
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                // 서비스에서 넣어준 정렬 조건을 스위치 케이스 문을 활용하여 셋팅하여 준다.
                switch (order.getProperty()) {
                    case "updatedAt":
                        return new OrderSpecifier(direction, comment.updatedAt);
                }
            }
        }
        return null;
    }

}
