package com.jujeol.member.application.dto;

import com.jujeol.member.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class MemberDto {

    private Long id;
    private String nickname;
    private String bio;

    public static MemberDto from(Member member) {
        return new MemberDto(member.getId(), member.getNickname().getNickname(),
                member.getBiography()
                        .getBiography());
    }

    public static MemberDto create(Long id, String nickname, String bio) {
        return new MemberDto(id, nickname, bio);
    }
}
