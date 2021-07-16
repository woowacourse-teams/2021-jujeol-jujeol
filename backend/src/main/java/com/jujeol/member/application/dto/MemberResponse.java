package com.jujeol.member.application.dto;

import com.jujeol.member.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberResponse {

    private Long id;

    public static MemberResponse from(Member member) {
        return new MemberResponse(member.getId());
    }
}
