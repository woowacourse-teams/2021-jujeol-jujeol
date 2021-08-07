package com.jujeol.drink.ui.dto;

import com.jujeol.drink.application.dto.MemberSimpleDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class MemberSimpleResponse {

    private Long id;
    private String name;

    public static MemberSimpleResponse create(MemberSimpleDto memberSimpleDto) {
        return new MemberSimpleResponse(
                memberSimpleDto.getId(),
                memberSimpleDto.getName()
        );
    }
}
