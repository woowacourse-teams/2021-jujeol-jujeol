package com.jujeol.member.controller;

import com.jujeol.DocsTestContext;
import com.jujeol.member.controller.response.LoginResponse;
import com.jujeol.member.domain.model.ProviderName;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

class LoginControllerTest extends DocsTestContext {

    @Test
    void 로그인_및_회원가입() {
        // given
        String code = "1234";
        ProviderName providerName = ProviderName.KAKAO;

        Map<String, Object> body = new HashMap<>();
        body.put("code", code);
        body.put("providerName", providerName);

        String accessToken = "accessToken";
        when(loginPresenter.login(any())).thenReturn(new LoginResponse(accessToken));

        // when
        MockMvcResponse response = given()
            .body(body)
            .when()
            .post("/login/token");

        // then
        response.prettyPrint();

        response.then()
            .apply(document("member/login/token"))
            .statusCode(HttpStatus.OK.value());
    }
}