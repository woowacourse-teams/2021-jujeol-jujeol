package com.jujeol.member.member.domain.repository;

import com.jujeol.member.member.domain.Member;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MemberCustomRepository {

    Optional<Member> findByProvideId(String provideId);

    List<Member> findOneStartingWithNicknameAndMostRecent(String nickname, Pageable pageable);
}
