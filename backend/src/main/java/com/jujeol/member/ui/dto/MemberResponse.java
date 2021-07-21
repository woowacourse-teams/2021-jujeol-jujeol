package com.jujeol.member.ui.dto;

import com.jujeol.member.application.dto.MemberDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MemberResponse {

    private Long id;
    private String nickname;
    private String bio;

    public static MemberResponse from(MemberDto memberDto) {
        return new MemberResponse(memberDto.getId(), memberDto.getNickname(), memberDto.getBio());
    }
}
