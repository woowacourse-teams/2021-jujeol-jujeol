package com.jujeol.member.domain.reader;

import com.jujeol.member.domain.model.Member;

import java.util.Optional;

public interface MemberReader {

    Optional<Member> findById(Long memberId);
}
