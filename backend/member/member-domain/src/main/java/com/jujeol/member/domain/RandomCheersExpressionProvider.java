package com.jujeol.member.domain;

import com.jujeol.member.domain.model.CheersExpression;
import com.jujeol.member.domain.model.CheersExpressionProvider;
import com.jujeol.member.domain.model.InmemoryCheersExpression;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
public class RandomCheersExpressionProvider implements CheersExpressionProvider {

    private static final Random random = new Random();

    @Override
    public CheersExpression getRandomCheersExpression() {
        List<InmemoryCheersExpression> cheersExpressions = InmemoryCheersExpression
            .getCheersExpressions();

        return cheersExpressions
            .get(random.nextInt(cheersExpressions.size()))
            .getCheersExpression();
    }
}
