package com.jujeol.member.rds.repository;

import com.jujeol.member.domain.model.Member;
import com.jujeol.member.rds.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Deprecated
@Component
@RequiredArgsConstructor
public class MemberPageRepository {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Optional<Member> findByNickname(String nickname, Pageable pageable) {
        return memberRepository.findByNickname(nickname, pageable).map(MemberEntity::toDomain);
    }
}
