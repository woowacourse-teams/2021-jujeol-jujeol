package com.jujeol.drink.drink.domain.repository;

import com.jujeol.drink.drink.domain.Drink;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.jujeol.drink.category.domain.QCategory.category;
import static com.jujeol.drink.drink.domain.QDrink.drink;
import static com.jujeol.preference.domain.QPreference.preference;

@RequiredArgsConstructor
public class DrinkRepositoryImpl implements DrinkCustomRepository {

    private final JPAQueryFactory factory;

    @Override
    public Optional<Drink> findByIdWithFetch(Long drinkId) {
        return Optional.ofNullable(
                factory.selectFrom(drink)
                        .join(drink.category, category)
                        .fetchJoin()
                        .where(drink.id.eq(drinkId))
                        .fetchOne());
    }

    @Override
    public Optional<Drink> findByName(String drinkName) {
        return Optional.ofNullable(
                factory.selectFrom(drink)
                        .where(drink.name.name.eq(drinkName))
                        .fetchOne());
    }

    @Override
    public List<Drink> findDrinks(Pageable pageable) {
        return factory.selectFrom(drink)
                .join(drink.category, category)
                .fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(drink.preferenceAvg.desc())
                .fetch();
    }

    @Override
    public List<Drink> findDrinksForMember(Long memberId, Pageable pageable) {
        return factory.selectFrom(drink)
                .join(drink.category, category)
                .fetchJoin()
                .where((isMemberAndGtThanRate(memberId)
                        .or(isNotMember(memberId))))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(drink.preferenceAvg.desc())
                .fetch();
    }

    @Override
    public List<Drink> findDrinksForMember(Long memberId, Pageable pageable, String categoryKey) {
        return factory.selectFrom(drink)
                .join(drink.category, category)
                .fetchJoin()
                .where((isMemberAndGtThanRate(memberId)
                        .or(isNotMember(memberId)))
                        .and(drink.category.key.eq(categoryKey)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(drink.preferenceAvg.desc())
                .fetch();
    }

    private BooleanExpression isMemberAndGtThanRate(Long memberId) {
        return drink.id.in(
                JPAExpressions
                        .select(preference.drink.id)
                        .from(preference)
                        .where(preference.member.id.eq(memberId)
                                .and(preference.rate.gt(3))));
    }

    private BooleanExpression isNotMember(Long memberId) {
        return drink.id.notIn(
                JPAExpressions
                        .select(preference.drink.id)
                        .from(preference)
                        .where(preference.member.id.eq(memberId))
                        .orderBy(drink.preferenceAvg.desc()));
    }

    @Override
    public Page<Drink> findAllByCategorySorted(String categoryKey, Pageable pageable) {
        QueryResults<Drink> result = factory.selectFrom(drink)
                .join(drink.category, category)
                .fetchJoin()
                .where(drink.category.key.eq(categoryKey))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(drink.preferenceAvg.desc())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    @Override
    public Page<Drink> findAllByCategory(String categoryKey, Pageable pageable) {
        QueryResults<Drink> result = factory.selectFrom(drink)
                .join(drink.category, category)
                .fetchJoin()
                .where(drink.category.key.eq(categoryKey))
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    @Override
    public Page<Drink> findAllSortByPreference(Pageable pageable) {
        QueryResults<Drink> result = factory.selectFrom(drink)
                .join(drink.category, category)
                .fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(drink.preferenceAvg.desc())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    @Override
    public List<Drink> findByKeyword(String keyword) {
        return factory.selectFrom(drink)
                .join(drink.category, category)
                .fetchJoin()
                .where(drink.name.name.like("%" + keyword + "%")
                        .or(drink.englishName.englishName.like("%" + keyword + "%")))
                .fetch();
    }

    @Override
    public List<Drink> findByCategory(String searchWord) {
        return factory.selectFrom(drink)
                .join(drink.category, category)
                .fetchJoin()
                .where(drink.category.name.eq(searchWord))
                .fetch();
    }
}
