package com.jujeol.review.domain.repository;

import com.jujeol.review.domain.Review;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.jujeol.drink.category.domain.QCategory.category;
import static com.jujeol.drink.drink.domain.QDrink.drink;
import static com.jujeol.member.member.domain.QMember.member;
import static com.jujeol.review.domain.QReview.review;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewCustomRepository {

    private final JPAQueryFactory factory;

    @Override
    public Page<Review> findAllByDrinkId(Long drinkId, Pageable pageable) {
        QueryResults<Review> result = factory.selectFrom(review)
                .join(review.member, member)
                .fetchJoin()
                .where(review.drink.id.eq(drinkId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    @Override
    public List<Review> findByDrinkIdAndMemberId(Long drinkId, Long memberId, Pageable pageable) {
        return factory.selectFrom(review)
                .where(review.drink.id.eq(drinkId).and(review.member.id.eq(memberId)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(review.createdAt.desc())
                .fetch();
    }

    @Override
    public Page<Review> findReviewsOfMine(Long memberId, Pageable pageable) {
        QueryResults<Review> result = factory.selectFrom(review)
                .join(review.drink, drink)
                .fetchJoin()
                .join(drink.category, category)
                .fetchJoin()
                .where(review.member.id.eq(memberId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(review.createdAt.desc())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }
}
