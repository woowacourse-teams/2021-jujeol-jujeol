package com.jujeol.member.domain.model;

import lombok.Getter;

@Getter
public class CheersExpression {

    private final String expression;
    private final String abbreviation;

    private CheersExpression(String expression, String abbreviation) {
        this.expression = expression;
        this.abbreviation = abbreviation;
    }

    public static CheersExpression create(String expression, String abbreviation) {
        return new CheersExpression(expression, abbreviation);
    }
}
