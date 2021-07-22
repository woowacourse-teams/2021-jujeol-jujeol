package com.jujeol.member.domain.nickname;

import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class InmemoryCheersExpressionFactory implements CheersExpressionsFactory {
    private static final Random random = new Random();

    @Override
    public CheersExpression getRandomCheersExpression() {
        List<InmemoryCheersExpression> cheersExpressions = InmemoryCheersExpression
                .getCheersExpressions();

        CheersExpression randomIndexExpression = cheersExpressions
                .get(random.nextInt(cheersExpressions.size()))
                .getCheersExpression();

        return randomIndexExpression;
    }
}
