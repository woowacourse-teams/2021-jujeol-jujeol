package com.jujeol.member.domain;

import static com.jujeol.member.domain.QMember.member;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean isExists(String nickname) {
        long memberCount = queryFactory.selectFrom(member)
                .where(member.nickname.nickname.eq(nickname))
                .fetchCount();
        return memberCount > 0;
    }
}
