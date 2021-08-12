package com.jujeol.member.member.domain.nickname;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CheersExpression {

    private String expression;
    private String abbreviation;

    public CheersExpression(String expression, String abbreviation) {
        this.expression = expression;
        this.abbreviation = abbreviation;
    }
}
