package com.jujeol.drink.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {

    private Long id;
    private MemberDto memberDto;
    private String content;

    public static ReviewResponse from(Long id, MemberDto memberDto, String content) {
        return new ReviewResponse(id, memberDto, content);
    }
}
