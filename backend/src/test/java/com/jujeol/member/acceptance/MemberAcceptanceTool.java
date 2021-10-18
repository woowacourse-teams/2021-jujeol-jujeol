package com.jujeol.member.acceptance;

import com.jujeol.member.auth.application.dto.TokenDto;
import com.jujeol.member.fixture.TestMember;
import com.jujeol.member.member.application.dto.PreferenceDto;
import com.jujeol.testtool.RequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

@Component
@ActiveProfiles("test")
public class MemberAcceptanceTool {

    @Autowired
    private RequestBuilder requestBuilder;

    public void 선호도_등록(Long drinkId, double preferenceRate, TestMember testMember) {
        requestBuilder.builder()
                .put("/members/me/drinks/" + drinkId + "/preference",
                        PreferenceDto.create(preferenceRate))
                .withUser(testMember)
                .build();
    }

    public String 로그인_토큰_반환(TestMember testMember) {
        return requestBuilder.builder()
                .post("/login/token", testMember.toDto())
                .build()
                .convertBody(TokenDto.class)
                .getAccessToken();
    }
}
