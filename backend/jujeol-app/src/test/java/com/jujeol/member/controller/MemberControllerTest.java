package com.jujeol.member.controller;

import com.jujeol.DocsTestContext;
import com.jujeol.member.controller.response.MemberInfoResponse;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

class MemberControllerTest extends DocsTestContext {

    @Test
    void 내_정보_조회() {
        // given
        String accessToken = "accessToken";
        fixedTokenProvider.fixToken(accessToken);
        fixedTokenProvider.createToken("1");

        when(memberPresenter.findMemberInfo(any()))
            .thenReturn(MemberInfoResponse.create(1L, "나봄", "hello"));

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

    @Test
    void 내_정보_수정() {
        // given
        String accessToken = "accessToken";
        fixedTokenProvider.fixToken(accessToken);
        fixedTokenProvider.createToken("1");

        Map<String, String> body = new HashMap<>();
        body.put("nickname", "나봄");
        body.put("bio", "헬로우");

        doNothing().when(memberPresenter).updateMe(any(), any());

        // when
        MockMvcResponse response = given()
            .header("Authorization", "Bearer " + accessToken)
            .body(body)
            .when()
            .put("/members/me");

        // then
        response.prettyPrint();

        response.then()
            .apply(document("member/update/me"))
            .statusCode(HttpStatus.OK.value());
    }
}