package com.jujeol.member.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.jujeol.AcceptanceTest;
import com.jujeol.commons.exception.ExceptionCodeAndDetails;
import com.jujeol.commons.exception.JujeolExceptionDto;
import com.jujeol.drink.exception.NotFoundDrinkException;
import com.jujeol.member.application.dto.MemberDto;
import com.jujeol.member.application.dto.PreferenceDto;
import com.jujeol.member.exception.UnauthorizedUserException;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class MemberAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("멤버 조회 - 성공")
    public void findMember() {
        //when
        Long id = request().get("/members/me")
                .withDocument("member/show/me")
                .withUser()
                .build()
                .convertBody(MemberDto.class)
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
        ExceptionCodeAndDetails codeAndDetails = ExceptionCodeAndDetails
                .findByClass(UnauthorizedUserException.class);

        //then
        assertThat(exception.getCode()).isEqualTo(codeAndDetails.getCode());
        assertThat(exception.getMessage()).isEqualTo(codeAndDetails.getMessage());
    }

    @Test
    @DisplayName("선호도 등록 - 성공")
    public void createPreference() {
        //given
        Long drinkId = 1L;
        PreferenceDto preferenceDto = PreferenceDto.of(4.5);

        //when
        ExtractableResponse<Response> response = request()
                .put("/members/me/drinks/" + drinkId + "/preference", preferenceDto)
                .withDocument("member/preference/create")
                .withUser()
                .build()
                .totalResponse();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("선호도 수정 - 성공")
    public void updatePreference() {
        //given
        Long drinkId = 1L;
        PreferenceDto preferenceDto = PreferenceDto.of(4.5);
        request()
                .put("/members/me/drinks/" + drinkId + "/preference", preferenceDto)
                .withUser()
                .build()
                .totalResponse();
        preferenceDto = PreferenceDto.of(3.0);

        //when
        ExtractableResponse<Response> updateResponse = request()
                .put("/members/me/drinks/" + drinkId + "/preference", preferenceDto)
                .withDocument("member/preference/update")
                .withUser()
                .build()
                .totalResponse();

        //then
        assertThat(updateResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("선호도 등록 및 수정 - 실패(비로그인 유저가 선호도 등록 및 수정 요청했을 때)")
    public void createPreference_fail_unauthorizedUser() {
        //given
        Long drinkId = 1L;
        PreferenceDto preferenceDto = PreferenceDto.of(4.5);

        //when
        ExtractableResponse<Response> response = request()
                .put("/members/me/drinks/" + drinkId + "/preference", preferenceDto)
                .withDocument("member/preference/create-fail-user")
                .build()
                .totalResponse();

        JujeolExceptionDto exception = response.as(JujeolExceptionDto.class);
        ExceptionCodeAndDetails codeAndDetails = ExceptionCodeAndDetails
                .findByClass(UnauthorizedUserException.class);

        //then
        assertThat(exception.getCode()).isEqualTo(codeAndDetails.getCode());
        assertThat(exception.getMessage()).isEqualTo(codeAndDetails.getMessage());
    }

    @Test
    @DisplayName("선호도 등록 및 수정 - 실패(해당 주류가 없을 때)")
    public void createPreference_fail_notFound() {
        //given
        Long drinkId = 100L;
        PreferenceDto preferenceDto = PreferenceDto.of(4.5);

        //when
        ExtractableResponse<Response> response = request()
                .put("/members/me/drinks/" + drinkId + "/preference", preferenceDto)
                .withDocument("member/preference/create-fail-drink")
                .withUser()
                .build()
                .totalResponse();

        JujeolExceptionDto exception = response.as(JujeolExceptionDto.class);
        ExceptionCodeAndDetails codeAndDetails = ExceptionCodeAndDetails
                .findByClass(NotFoundDrinkException.class);

        //then
        assertThat(exception.getCode()).isEqualTo(codeAndDetails.getCode());
        assertThat(exception.getMessage()).isEqualTo(codeAndDetails.getMessage());
    }

    @Test
    @DisplayName("선호도 삭제 - 성공")
    public void deletePreference() {
        //given
        Long drinkId = 1L;
        PreferenceDto preferenceDto = PreferenceDto.of(4.5);

        request()
                .put("/members/me/drinks/" + drinkId + "/preference", preferenceDto)
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
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("선호도 삭제 - 실패(비로그인 유저가 선호도 등록 및 수정 요청했을 때)")
    public void deletePreference_fail_unauthorizedUser() {
        //given
        Long drinkId = 1L;

        //when
        ExtractableResponse<Response> response = request()
                .delete("/members/me/drinks/" + drinkId + "/preference")
                .withDocument("member/preference/delete-fail-user")
                .build()
                .totalResponse();

        JujeolExceptionDto exception = response.as(JujeolExceptionDto.class);
        ExceptionCodeAndDetails codeAndDetails = ExceptionCodeAndDetails
                .findByClass(UnauthorizedUserException.class);

        //then
        assertThat(exception.getCode()).isEqualTo(codeAndDetails.getCode());
        assertThat(exception.getMessage()).isEqualTo(codeAndDetails.getMessage());
    }
}
