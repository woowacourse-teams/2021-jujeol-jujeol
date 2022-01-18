package com.jujeol.member.member.domain.repository;

import com.jujeol.member.member.domain.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.jujeol.member.member.domain.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberCustomRepository {

    private final JPAQueryFactory factory;

    @Override
    public Optional<Member> findByProvideId(String provideId) {
        return Optional.ofNullable(factory.selectFrom(member)
                .where(member.provider.provideId.eq(provideId))
                .fetchOne());
    }

    @Override
    public List<Member> findOneStartingWithNicknameAndMostRecent(String nickname, Pageable pageable) {
        return factory.selectFrom(member)
                .where(member.nickname.nickname.like(nickname + "%"))
                .orderBy(member.createdAt.desc())
                .fetch();
    }
}
