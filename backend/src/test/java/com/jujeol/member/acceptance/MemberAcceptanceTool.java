package com.jujeol.member.acceptance;

import com.jujeol.member.auth.application.dto.SocialProviderCodeDto;
import com.jujeol.member.auth.application.dto.TokenDto;
import com.jujeol.member.auth.domain.Provider;
import com.jujeol.member.auth.domain.ProviderName;
import com.jujeol.member.fixture.TestMember;
import com.jujeol.member.member.application.dto.PreferenceDto;
import com.jujeol.member.member.domain.Biography;
import com.jujeol.member.member.domain.Member;
import com.jujeol.member.member.domain.nickname.Nickname;
import com.jujeol.member.member.domain.repository.MemberRepository;
import com.jujeol.testtool.RequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

@Component
@ActiveProfiles("test")
public class MemberAcceptanceTool {

    @Autowired
    private RequestBuilder requestBuilder;
    @Autowired
    private MemberRepository memberRepository;

    public void 선호도_등록(Long drinkId, double preferenceRate, TestMember testMember) {
        requestBuilder.builder()
                .put("/members/me/drinks/" + drinkId + "/preference",
                        PreferenceDto.create(preferenceRate))
                .withUser(testMember)
                .build();
    }

    public String 로그인_토큰_반환(TestMember testMember) {
        final SocialProviderCodeDto socialProviderCodeDto = testMember.toDto();
        return requestBuilder.builder()
                .post("/login/token", testMember.toDto())
                .build()
                .convertBody(TokenDto.class)
                .getAccessToken();
    }

    public Member 회원가입(String nickname) {
        final Provider provider = Provider.create("0000", ProviderName.TEST);
        final Nickname targetNickname = Nickname.create(nickname);
        final Biography biography = Biography.create("this is test");
        final Member member = Member.create(provider, targetNickname, biography);
        return memberRepository.save(member);
    }
}
