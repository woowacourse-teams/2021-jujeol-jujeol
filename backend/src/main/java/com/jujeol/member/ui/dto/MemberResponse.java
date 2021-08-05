package com.jujeol.member.ui.dto;

import com.jujeol.member.application.dto.MemberDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class MemberResponse {

    private Long id;
    private String nickname;
    private String bio;

    public static MemberResponse create(MemberDto memberDto) {
        return new MemberResponse(memberDto.getId(), memberDto.getNickname(), memberDto.getBio());
    }
}
