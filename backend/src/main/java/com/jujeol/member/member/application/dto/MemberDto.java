package com.jujeol.member.member.application.dto;

import com.jujeol.member.member.domain.Member;
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

    public static MemberDto create(Member member) {
        return new MemberDto(
                member.getId(),
                member.getNickname().getNickname(),
                member.getBiography().getBiography()
        );
    }

    public static MemberDto create(Long id, String nickname, String bio) {
        return new MemberDto(id, nickname, bio);
    }
}
