package com.jujeol.member.application;

import com.jujeol.member.application.dto.MemberResponse;
import com.jujeol.member.domain.LoginMember;
import com.jujeol.member.domain.Member;
import com.jujeol.member.domain.MemberRepository;
import com.jujeol.member.exception.NoSuchMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponse findMember(LoginMember loginMember) {
        Member member = memberRepository.findById(loginMember.getId())
                .orElseThrow(NoSuchMemberException::new);
        return MemberResponse.of(member);
    }
}
