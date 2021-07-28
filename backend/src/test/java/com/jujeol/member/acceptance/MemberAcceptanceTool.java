package com.jujeol.member.acceptance;

import com.jujeol.RequestBuilder;
import com.jujeol.drink.domain.Drink;
import com.jujeol.member.application.dto.PreferenceDto;
import com.jujeol.member.fixture.TestMember;
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
                .put("/members/me/drinks/" + drinkId + "/preference", PreferenceDto.of(preferenceRate))
                .withUser(testMember)
                .build();
    }
}
