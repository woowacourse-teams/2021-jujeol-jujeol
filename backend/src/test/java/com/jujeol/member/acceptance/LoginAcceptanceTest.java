package com.jujeol.member.acceptance;

import static com.jujeol.member.fixture.TestsCheersExpressionFactory.TEST_NICKNAME_PREFIX;
import static org.assertj.core.api.Assertions.assertThat;

import com.jujeol.AcceptanceTest;
import com.jujeol.member.application.dto.MemberDto;
import com.jujeol.member.application.dto.TokenDto;
import com.jujeol.member.fixture.SocialLoginMemberFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoginAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("로그인 - 성공")
    public void login() {
        //given
        //when
        final String accessToken = request()
                .post("login/token", SocialLoginMemberFixture.DEFAULT.toDto())
                .withDocument("member/login/token")
                .build()
                .convertBody(TokenDto.class)
                .getAccessToken();

        assertThat(accessToken).isNotEmpty();
    }


    @Test
    @DisplayName("회원가입 후 닉네임 자동 생성 확인 - 성공")
    public void autoCreateNicknameTest(){
        //given
        String accessToken = 회원가입을_하고(SocialLoginMemberFixture.PIKA);

        //when
        String nickname = requestWithOtherUser(accessToken)
                .get("/members/me")
                .withUser()
                .build()
                .convertBody(MemberDto.class)
                .getNickname();

        //then
        assertThat(nickname).contains(TEST_NICKNAME_PREFIX);
    }
}
