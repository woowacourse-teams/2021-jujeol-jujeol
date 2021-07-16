package com.jujeol.member.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("소셜에서 주는 id를 통한 회원 찾기")
    public void findByProvideIdTest() {
        //given
        String provideId = "1";
        Provider provider = Provider.create(provideId, ProviderName.TEST);
        memberRepository.save(Member.create(provider));

        //when
        Member member = memberRepository.findByProvideId(provider.getProvideId()).get();
        Provider savedProvider = member.getProvider();

        //then
        assertThat(savedProvider.getProvideId()).isEqualTo(provideId);
        assertThat(savedProvider.getProviderName()).isEqualTo(ProviderName.TEST);
    }
}
