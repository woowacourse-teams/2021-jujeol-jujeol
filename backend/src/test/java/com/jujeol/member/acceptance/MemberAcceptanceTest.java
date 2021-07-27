package com.jujeol.member.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.jujeol.AcceptanceTest;
import com.jujeol.RequestBuilder.Option;
import com.jujeol.commons.exception.ExceptionCodeAndDetails;
import com.jujeol.commons.exception.JujeolExceptionDto;
import com.jujeol.drink.exception.NotFoundDrinkException;
import com.jujeol.drink.ui.dto.DrinkDetailResponse;
import com.jujeol.member.application.dto.PreferenceDto;
import com.jujeol.member.exception.UnauthorizedUserException;
import com.jujeol.member.ui.dto.MemberRequest;
import com.jujeol.member.ui.dto.MemberResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class MemberAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("멤버 조회 - 성공")
    public void findMember() {
        //given
        //when
        Long id = 내_정보를_조회한다("member/show/me").getId();

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
    @DisplayName("멤버 수정 - 성공")
    public void updateMember() {
        //when
        String updateNickname = "열글자_닉네임_쁘이";
        String updateBiography = "적절한 길이의 자기소개가 필요합니다. 적절한 길이가 몇글자?35";

        ExtractableResponse<Response> response = 내_정보를_수정한다(
                new MemberRequest(updateNickname, updateBiography), "member/update/me");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

        MemberResponse memberResponse = 내_정보를_조회한다();

        //then
        assertThat(memberResponse.getNickname()).isEqualTo(updateNickname);
        assertThat(memberResponse.getBio()).isEqualTo(updateBiography);
    }

    @Test
    @DisplayName("멤버 수정 - 실패 (부적절한 닉네임)")
    public void updateMember_fail_InvalidNickname() {
        //when
        String updateNickname = "딱_열글자_넘기는_놈";
        String updateBiography = "적절한 길이의 자기소개가 필요합니다. 적절한 길이가 몇글자?35";

        ExtractableResponse<Response> response = 내_정보를_수정한다(
                new MemberRequest(updateNickname, updateBiography),
                "member/update/me-fail-nickname");

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("멤버 수정 - 실패 (부적절한 자기소개)")
    public void updateMember_InvalidBio() {
        //when
        String updateNickname = "열글자_닉네임_쁘이";
        String updateBiography = "제가 LA 에인절스에 있을 때가 생각나는군요. 그떄는 36글자를 ";

        ExtractableResponse<Response> response = 내_정보를_수정한다(
                new MemberRequest(updateNickname, updateBiography), "member/update/me-fail-bio");

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
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

        DrinkDetailResponse drinkDetailResponse = request()
                .get("/drinks/1")
                .withUser()
                .build().convertBody(DrinkDetailResponse.class);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(drinkDetailResponse.getPreferenceAvg()).isEqualTo(4.5);
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

        DrinkDetailResponse drinkDetailResponse = request()
                .get("/drinks/1")
                .withUser()
                .build().convertBody(DrinkDetailResponse.class);

        //then
        assertThat(updateResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(drinkDetailResponse.getPreferenceAvg()).isEqualTo(3.0);
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

        DrinkDetailResponse drinkDetailResponse = request()
                .get("/drinks/1")
                .withUser()
                .build().convertBody(DrinkDetailResponse.class);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(drinkDetailResponse.getPreferenceAvg()).isEqualTo(0.0);
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

    private ExtractableResponse<Response> 내_정보를_수정한다(MemberRequest memberRequest) {
        return 내_정보를_수정한다(memberRequest, "");
    }

    private ExtractableResponse<Response> 내_정보를_수정한다(MemberRequest memberRequest,
            String documentPath) {
        Option updateRequest = request().put("/members/me", memberRequest);

        if (!documentPath.isBlank()) {
            updateRequest.withDocument(documentPath);
        }

        return updateRequest.withUser()
                .build()
                .totalResponse();
    }

    private MemberResponse 내_정보를_조회한다() {
        return 내_정보를_조회한다("");
    }

    private MemberResponse 내_정보를_조회한다(String documentPath) {
        Option getRequest = request().get("/members/me");
        if (!documentPath.isBlank()) {
            getRequest.withDocument(documentPath);
        }
        return getRequest.withUser()
                .build()
                .convertBody(MemberResponse.class);
    }
}
