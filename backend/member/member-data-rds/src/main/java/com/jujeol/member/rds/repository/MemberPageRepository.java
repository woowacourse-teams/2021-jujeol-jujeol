package com.jujeol.member.rds.repository;

import com.jujeol.member.domain.model.Member;
import com.jujeol.member.rds.entity.MemberEntity;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Deprecated
@Component
@RequiredArgsConstructor
public class MemberPageRepository {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Optional<Member> findByNickname(String nickname) {
        return memberRepository.findByNickname(nickname).map(MemberEntity::toDomain);
    }
}
