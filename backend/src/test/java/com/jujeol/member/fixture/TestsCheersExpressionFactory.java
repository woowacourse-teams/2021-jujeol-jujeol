package com.jujeol.member.fixture;

import com.jujeol.member.domain.nickname.CheersExpression;
import com.jujeol.member.domain.nickname.CheersExpressionsFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class TestsCheersExpressionFactory implements CheersExpressionsFactory {
    public static String TEST_NICKNAME_PREFIX = "니가사";
    public static String TEST_BIO = "니가 사는 거라면 나도 끼지";

    @Override
    public CheersExpression getRandomCheersExpression() {
        return new CheersExpression(TEST_BIO, TEST_NICKNAME_PREFIX);
    }
}
