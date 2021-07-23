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
    private String nickname;
    private String bio;

    public static MemberResponse from(Member member) {
        return new MemberResponse(member.getId(), "청바지_123", "청춘은 바로 지금!");
    }
}
