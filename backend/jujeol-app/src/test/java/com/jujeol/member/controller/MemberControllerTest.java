package com.jujeol.member.controller;

import com.jujeol.DocsTestContext;
import com.jujeol.member.controller.response.LoginResponse;
import com.jujeol.member.controller.response.MemberInfoResponse;
import com.jujeol.member.domain.model.ProviderName;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

class MemberControllerTest extends DocsTestContext {

    @Test
    void 내_정보_조회() {
        // given
        String accessToken = "accessToken";
        when(memberPresenter.findMemberInfo(any()))
            .thenReturn(MemberInfoResponse.create(1L, "나봄", "hello"));
        fixedTokenProvider.fixToken(accessToken);
        fixedTokenProvider.createToken("1");

        // when
        MockMvcResponse response = given()
            .header("Authorization", "Bearer " + accessToken)
            .when()
            .get("/members/me");

        // then
        response.prettyPrint();

        response.then()
            .apply(document("member/show/me"))
            .statusCode(HttpStatus.OK.value());
    }
}