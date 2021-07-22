package com.jujeol.member.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.jujeol.member.domain.nickname.Member;
import com.jujeol.member.domain.nickname.MemberRepository;
import com.jujeol.member.domain.nickname.Nickname;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class MemberRepositoryTest {
    private final static String TEST_PROVIDER_ID = "1";

    private final Provider testProvider = Provider.of(TEST_PROVIDER_ID, ProviderName.TEST);

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("소셜에서 주는 id를 통한 회원 찾기")
    public void findByProvideIdTest() {
        //given
        memberRepository.save(Member.create(testProvider, null, null));

        //when
        Member member = memberRepository.findByProvideId(testProvider.getProvideId()).get();
        Provider savedProvider = member.getProvider();

        //then
        assertThat(savedProvider.getProvideId()).isEqualTo(TEST_PROVIDER_ID);
        assertThat(savedProvider.getProviderName()).isEqualTo(ProviderName.TEST);
    }

    @Test
    @DisplayName("이름이 포함되는 것과 생성시간 최신순 조건 단건 검색 - 성공")
    public void findOneStartingWithNicknameAndMostRecentTest() {
        //given
        String prefix = "닉네임";
        Biography testBiography = Biography.create("테스트 자기소개");

        //when
        //then
        Member expectedMember = Member.create(testProvider, Nickname.create(prefix + "_3"), testBiography);

        memberRepository.save(Member.create(testProvider, Nickname.create(prefix + "_1"), testBiography));
        memberRepository.save(Member.create(testProvider, Nickname.create(prefix + "_2"), testBiography));
        memberRepository.save(expectedMember);
        assertThat(findMember(prefix)).isEqualTo(expectedMember);

        memberRepository.save(Member.create(testProvider, Nickname.create("123" + prefix), testBiography));
        memberRepository.save(Member.create(testProvider, Nickname.create("1234" + prefix), testBiography));
        assertThat(findMember(prefix)).isEqualTo(expectedMember);
    }

    private Member findMember(String prefix) {
        return memberRepository
                .findOneStartingWithNicknameAndMostRecent(prefix, PageRequest.of(0, 1)).get(0);
    }
}
