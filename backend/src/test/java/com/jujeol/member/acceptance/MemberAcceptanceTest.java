package com.jujeol.member.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.jujeol.AcceptanceTest;
import com.jujeol.commons.exception.JujeolExceptionDto;
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

        JujeolExceptionDto exception = response.as(JujeolExceptionDto.class);

        //then
        assertThat(exception.getCode()).isEqualTo("1005");
        assertThat(exception.getMessage()).isEqualTo("권한이 없는 유저입니다.");
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
    @DisplayName("선호도 등록 - 실패(비로그인 유저가 선호도 등록 요청했을 때)")
    public void createPreference_fail_unauthorizedUser() {
        //given
        Long drinkId = 1L;
        PreferenceRequest preferenceRequest = new PreferenceRequest(4.5);

        //when
        ExtractableResponse<Response> response = request()
                .put("/members/me/drinks/" + drinkId + "/preference", preferenceRequest)
                .build()
                .totalResponse();

        JujeolExceptionDto exception = response.as(JujeolExceptionDto.class);

        //then
        assertThat(exception.getCode()).isEqualTo("1005");
        assertThat(exception.getMessage()).isEqualTo("권한이 없는 유저입니다.");
    }

    @Test
    @DisplayName("선호도 등록 - 실패(비로그인 유저가 선호도 등록 요청했을 떄)")
    public void createPreference_fail_notFound() {
        //given
        Long drinkId = 100L;
        PreferenceRequest preferenceRequest = new PreferenceRequest(4.5);

        //when
        ExtractableResponse<Response> response = request()
                .put("/members/me/drinks/" + drinkId + "/preference", preferenceRequest)
                .withUser()
                .build()
                .totalResponse();

        JujeolExceptionDto exception = response.as(JujeolExceptionDto.class);

        //then
        assertThat(exception.getCode()).isEqualTo("2003");
        assertThat(exception.getMessage()).isEqualTo("해당하는 id의 상품을 찾을 수 없습니다.");
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
