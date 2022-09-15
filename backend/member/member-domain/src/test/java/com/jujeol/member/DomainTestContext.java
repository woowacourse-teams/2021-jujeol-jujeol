package com.jujeol.member;

import com.jujeol.member.domain.FixedTokenProvider;
import com.jujeol.member.rds.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public abstract class DomainTestContext {
    @Autowired
    protected MemberRepository memberRepository;
    @Autowired
    protected FixedTokenProvider tokenProvider;

    @AfterEach
    void tearDown() {
        memberRepository.deleteAll();
    }
}
