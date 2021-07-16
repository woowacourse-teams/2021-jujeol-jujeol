package com.jujeol.member.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.jujeol.AcceptanceTest;
import com.jujeol.member.application.dto.MemberResponse;
import com.jujeol.member.application.dto.PreferenceRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("멤버 조회 - 성공")
    public void findMember() {
        //when
        Long id = request().get("/members/me")
                .withDocument("member/show/me")
                .withUser()
                .build()
                .convertBody(MemberResponse.class)
                .getId();

        //then
        assertThat(id).isEqualTo(1L);
    }

    @Test
    @DisplayName("멤버 조회 - 실패")
    public void findMember_fail_NoSuchMember() {
        //when
        ExtractableResponse<Response> response = request().get("/members/me")
                .withDocument("member/show/me-fail")
                .build()
                .totalResponse();

        //then
        assertThat(response.statusCode()).isEqualTo(400);
    }

    @Test
    @DisplayName("선호도 등록 - 성공")
    public void createPreference() {
        //given
        Long drinkId = 1L;
        PreferenceRequest preferenceRequest = new PreferenceRequest(4.5);

        //when
        ExtractableResponse<Response> response = request()
                .put("/members/me/drinks/" + drinkId + "/preference", preferenceRequest)
                .withDocument("member/preference/create")
                .withUser()
                .build()
                .totalResponse();

        //then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test
    @DisplayName("선호도 수정 - 성공")
    public void updatePreference() {
        //given
        Long drinkId = 1L;
        PreferenceRequest preferenceRequest = new PreferenceRequest(4.5);
        request()
                .put("/members/me/drinks/" + drinkId + "/preference", preferenceRequest)
                .withUser()
                .build()
                .totalResponse();
        preferenceRequest = new PreferenceRequest(3.0);

        //when
        ExtractableResponse<Response> updateResponse = request()
                .put("/members/me/drinks/" + drinkId + "/preference", preferenceRequest)
                .withDocument("member/preference/update")
                .withUser()
                .build()
                .totalResponse();

        //then
        assertThat(updateResponse.statusCode()).isEqualTo(200);
    }

    @Test
    @DisplayName("선호도 삭제 - 성공")
    public void deletePreference() {
        //given
        Long drinkId = 1L;
        PreferenceRequest preferenceRequest = new PreferenceRequest(4.5);

        request()
                .put("/members/me/drinks/" + drinkId + "/preference", preferenceRequest)
                .withUser()
                .build()
                .totalResponse();

        //when
        ExtractableResponse<Response> response = request()
                .delete("/members/me/drinks/" + drinkId + "/preference")
                .withDocument("member/preference/delete")
                .withUser()
                .build()
                .totalResponse();

        //then
        assertThat(response.statusCode()).isEqualTo(200);
    }
}
