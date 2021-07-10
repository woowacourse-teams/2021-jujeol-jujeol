package com.jujeol.member.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.jujeol.AcceptanceTest;
import com.jujeol.member.application.dto.TokenRequest;
import com.jujeol.member.application.dto.TokenResponse;
import com.jujeol.member.domain.ProviderName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoginAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("로그인 - 성공")
    public void login() {
        //given
        TokenRequest tokenRequest = new TokenRequest("1234", ProviderName.TEST);

        //when
        final String accessToken = request()
                .post("login/token", tokenRequest)
                .withDocument("member/login/token")
                .build()
                .convertBody(TokenResponse.class)
                .getAccessToken();

        assertThat(accessToken).isNotEmpty();
    }
}
