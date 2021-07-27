package com.jujeol.member.acceptance;

import static com.jujeol.member.fixture.TestMember.PIKA;
import static com.jujeol.member.fixture.TestsCheersExpressionRepository.TEST_NICKNAME_PREFIX;
import static org.assertj.core.api.Assertions.assertThat;

import com.jujeol.AcceptanceTest;
import com.jujeol.member.application.dto.MemberDto;
import com.jujeol.member.application.dto.TokenDto;
import com.jujeol.member.fixture.TestMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoginAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("로그인 - 성공")
    public void login() {
        //given
        //when
        final String accessToken = request()
                .post("login/token", TestMember.DEFAULT.toDto())
                .withDocument("member/login/token")
                .build()
                .convertBody(TokenDto.class)
                .getAccessToken();

        assertThat(accessToken).isNotEmpty();
    }


    @Test
    @DisplayName("회원가입 후 닉네임 자동 생성 확인 - 성공")
    public void autoCreateNicknameTest(){
        //when
        String nickname = request()
                .get("/members/me")
                .withUser(PIKA)
                .build()
                .convertBody(MemberDto.class)
                .getNickname();

        //then
        assertThat(nickname).contains(TEST_NICKNAME_PREFIX);
    }
}
