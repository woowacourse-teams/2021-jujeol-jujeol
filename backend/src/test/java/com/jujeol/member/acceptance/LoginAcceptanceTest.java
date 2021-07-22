package com.jujeol.member.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.jujeol.AcceptanceTest;
import com.jujeol.member.application.dto.TokenDto;
import com.jujeol.member.domain.ProviderName;
import com.jujeol.member.ui.dto.SocialProviderCodeRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoginAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("로그인 - 성공")
    public void login() {
        //given
        SocialProviderCodeRequest socialProviderCodeRequest = SocialProviderCodeRequest
                .of("1234", ProviderName.TEST);

        //when
        final String accessToken = request()
                .post("login/token", socialProviderCodeRequest)
                .withDocument("member/login/token")
                .build()
                .convertBody(TokenDto.class)
                .getAccessToken();

        assertThat(accessToken).isNotEmpty();
    }

}
