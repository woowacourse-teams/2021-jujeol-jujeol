package com.jujeol.preference.domain.repository;

import com.jujeol.preference.domain.Preference;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.jujeol.drink.category.domain.QCategory.category;
import static com.jujeol.drink.drink.domain.QDrink.drink;
import static com.jujeol.member.member.domain.QMember.member;
import static com.jujeol.preference.domain.QPreference.preference;

@RequiredArgsConstructor
public class PreferenceRepositoryImpl implements PreferenceCustomRepository {

    private final JPAQueryFactory factory;

    @Override
    public Optional<Double> averageOfPreferenceRate(Long drinkId) {
        return Optional.ofNullable(
                factory.select(preference.rate.avg())
                        .from(preference)
                        .where(preference.drink.id.eq(drinkId))
                        .fetchOne());
    }

    @Override
    public Page<Preference> findByMemberIdOrderByCreatedAtDesc(Long memberId, Pageable pageable) {
        QueryResults<Preference> result = factory.selectFrom(preference)
                .join(preference.drink, drink)
                .fetchJoin()
                .join(drink.category, category)
                .fetchJoin()
                .join(preference.member, member)
                .fetchJoin()
                .where(preference.member.id.eq(memberId))
                .orderBy(preference.createdAt.desc())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    @Override
    public List<Preference> findAllByCategory(String category) {
        return factory.selectFrom(preference)
                .where(preference.drink.category.name.eq(category))
                .fetch();
    }
}
