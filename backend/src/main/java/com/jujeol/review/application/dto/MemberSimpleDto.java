package com.jujeol.review.application.dto;

import com.jujeol.member.member.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class MemberSimpleDto {

    private Long id;
    private String name;

    public static MemberSimpleDto create(Member member) {
        return new MemberSimpleDto(
                member.getId(),
                member.getStringNickname()
        );
    }
}
