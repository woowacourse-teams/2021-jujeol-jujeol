package com.jujeol;

import com.jujeol.conf.FixedCheersExpressionProvider;
import com.jujeol.conf.FixedTokenProvider;
import com.jujeol.drink.rds.repository.CategoryRepository;
import com.jujeol.drink.rds.repository.DrinkRepository;
import com.jujeol.member.rds.repository.MemberRepository;
import com.jujeol.oauth.model.SocialClient;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public abstract class IntegrationTestContext {

    @Autowired
    protected CategoryRepository categoryRepository;

    @Autowired
    protected DrinkRepository drinkRepository;

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected FixedCheersExpressionProvider fixedCheersExpressionProvider;

    @Autowired
    protected FixedTokenProvider fixedTokenProvider;

    /*
    Clients
     */
    @MockBean
    protected SocialClient socialClient;

    @AfterEach
    void tearDown() {
        drinkRepository.deleteAll();
        categoryRepository.deleteAll();
        memberRepository.deleteAll();
        fixedTokenProvider.reset();
    }
}
