package com.jujeol.conf;

import com.jujeol.member.domain.model.CheersExpression;
import com.jujeol.member.domain.model.CheersExpressionProvider;
import org.springframework.stereotype.Component;

@Component
public class FixedCheersExpressionProvider implements CheersExpressionProvider {

    private String expression;
    private String abbreviation;

    @Override
    public CheersExpression getRandomCheersExpression() {
        return CheersExpression.create(expression, abbreviation);
    }

    public void fixExpression(String expression, String abbreviation) {
        this.expression = expression;
        this.abbreviation = abbreviation;
    }
}
