package com.jujeol.member.member.domain.nickname;

import java.util.List;
import java.util.Random;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"dev", "prod", "local", "dummy"})
public class InmemoryCheersExpressionRepository implements CheersExpressionsRepository {

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
