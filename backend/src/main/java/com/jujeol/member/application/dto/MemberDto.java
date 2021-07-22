package com.jujeol.member.application.dto;

import com.jujeol.member.domain.nickname.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MemberDto {

    private Long id;
    private String nickname;
    private String bio;

    public static MemberDto from(Member member) {
        return new MemberDto(member.getId(), "청바지_123", "청춘은 바로 지금!");
    }
}
