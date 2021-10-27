package com.jujeol.member.acceptance;

import static com.jujeol.commons.exception.ExceptionCodeAndDetails.NOT_FOUND_DRINK;
import static com.jujeol.commons.exception.ExceptionCodeAndDetails.UNAUTHORIZED_USER;
import static com.jujeol.drink.DrinkTestContainer.OB;
import static com.jujeol.member.fixture.TestMember.CROFFLE;
import static com.jujeol.member.fixture.TestMember.WEDGE;
import static org.assertj.core.api.Assertions.assertThat;

import com.jujeol.AcceptanceTest;
import com.jujeol.admin.acceptance.AdminAcceptanceTool;
import com.jujeol.drink.DrinkTestContainer;
import com.jujeol.drink.acceptance.DrinkAcceptanceTool;
import com.jujeol.drink.drink.ui.dto.DrinkResponse;
import com.jujeol.member.fixture.TestMember;
import com.jujeol.member.member.application.dto.PreferenceDto;
import com.jujeol.member.member.ui.dto.MemberRequest;
import com.jujeol.member.member.ui.dto.MemberResponse;
import com.jujeol.testtool.option.RequestOption;
import com.jujeol.testtool.response.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class MemberAcceptanceTest extends AcceptanceTest {

    @Autowired
    private AdminAcceptanceTool adminAcceptanceTool;
    @Autowired
    private DrinkAcceptanceTool drinkAcceptanceTool;
    @Autowired
    private MemberAcceptanceTool memberAcceptanceTool;

    @Test
    @DisplayName("멤버 조회 - 성공")
    public void findMember() {
        //when
        final MemberResponse memberResponse = 내_정보를_조회한다("member/show/me", WEDGE);

        //then
        assertThat(memberResponse).isNotNull();
    }

    @Test
    @DisplayName("멤버 조회 - 실패(비로그인)")
    public void findMember_fail_NoSuchMember() {
        //when
        final HttpResponse httpResponse = request().get("/members/me")
                .withDocument("member/show/me-fail")
                .build();

        //then
        예외_검증(httpResponse.errorResponse(), UNAUTHORIZED_USER);
    }

    @Test
    @DisplayName("멤버 수정 - 성공")
    public void updateMember() {
        //when
        String updateNickname = "열글자_닉네임_쁘이";
        String updateBiography = "적절한 길이의 자기소개가 필요합니다. 적절한 길이가 몇글자?35";

        HttpResponse response = 내_정보를_수정한다(WEDGE,
                new MemberRequest(updateNickname, updateBiography), "member/update/me");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK);

        //then
        MemberResponse memberResponse = 내_정보를_조회한다(WEDGE);
        assertThat(memberResponse.getNickname()).isEqualTo(updateNickname);
        assertThat(memberResponse.getBio()).isEqualTo(updateBiography);
    }

    @Test
    @DisplayName("멤버 수정 - 실패 (부적절한 닉네임)")
    public void updateMember_fail_InvalidNickname() {
        //when
        String updateNickname = "딱_열글자_넘기는_놈";
        String updateBiography = "적절한 길이의 자기소개가 필요합니다. 적절한 길이가 몇글자?35";

        HttpResponse response = 내_정보를_수정한다(WEDGE,
                new MemberRequest(updateNickname, updateBiography),
                "member/update/me-fail-nickname");

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("멤버 수정 - 실패 (중복 닉네임)")
    public void updateMember_fail_duplicateNickname() {
        //when
        String updateNickname = "나봄";
        memberAcceptanceTool.회원가입(updateNickname);

        HttpResponse response = 내_정보를_수정한다(WEDGE,
                new MemberRequest(updateNickname, "test"),
                "member/update/me-duplicate-nickname");

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.errorResponse().getCode()).isEqualTo("1010");
        assertThat(response.errorResponse().getMessage()).isEqualTo("닉네임이 중복되었습니다.");
    }

    @Test
    @DisplayName("멤버 수정 - 실패 (부적절한 자기소개)")
    public void updateMember_InvalidBio() {
        //when
        String updateNickname = "열글자_닉네임_쁘이";
        String updateBiography = "제가 LA 에인절스에 있을 때가 생각나는군요. 그떄는 36글자를 ";

        HttpResponse response = 내_정보를_수정한다(WEDGE,
                new MemberRequest(updateNickname, updateBiography), "member/update/me-fail-bio");

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("선호도 등록 - 성공")
    public void createPreference() {
        //given
        final Long obId = 주류_등록(OB);
        PreferenceDto preferenceDto = PreferenceDto.create(4.5);

        //when
        HttpResponse response = request()
                .put("/members/me/drinks/{drinkId}/preference", preferenceDto, obId)
                .withDocument("member/preference/create")
                .withUser(CROFFLE)
                .build();

        //then
        final DrinkResponse drinkResponse = drinkAcceptanceTool.단일_상품_조회(obId);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK);
        assertThat(drinkResponse.getPreferenceAvg()).isEqualTo(4.5);
    }

    @Test
    @DisplayName("선호도 수정 - 성공")
    public void updatePreference() {
        //given
        final Long obId = 주류_등록(OB);
        memberAcceptanceTool.선호도_등록(obId, 4.5, CROFFLE);
        PreferenceDto newPreference = PreferenceDto.create(3.0);

        //when
        HttpResponse updateResponse = request()
                .put("/members/me/drinks/{drinkId}/preference", newPreference, obId)
                .withDocument("member/preference/update")
                .withUser(CROFFLE)
                .build();

        //then
        final DrinkResponse drinkResponse = drinkAcceptanceTool.단일_상품_조회(obId);
        assertThat(updateResponse.statusCode()).isEqualTo(HttpStatus.OK);
        assertThat(drinkResponse.getPreferenceAvg()).isEqualTo(3.0);
    }

    @Test
    @DisplayName("선호도 등록 및 수정 - 실패(비로그인 유저가 선호도 등록 및 수정 요청했을 때)")
    public void createPreference_fail_unauthorizedUser() {
        //given
        final Long obId = 주류_등록(OB);
        memberAcceptanceTool.선호도_등록(obId, 3.5, CROFFLE);
        PreferenceDto preferenceDto = PreferenceDto.create(4.5);

        //when
        final HttpResponse httpResponse = request()
                .put("/members/me/drinks/{drinkId}/preference", preferenceDto, obId)
                .withDocument("member/preference/create-fail-user")
                .build();

        //then
        예외_검증(httpResponse.errorResponse(), UNAUTHORIZED_USER);
    }

    @Test
    @DisplayName("선호도 등록 및 수정 - 실패(해당 주류가 없을 때)")
    public void createPreference_fail_notFound() {
        //given
        final Long obId = 주류_등록(OB);
        memberAcceptanceTool.선호도_등록(obId, 3.5, CROFFLE);
        PreferenceDto preferenceDto = PreferenceDto.create(4.5);

        //when
        final HttpResponse httpResponse = request()
                .put("/members/me/drinks/{drinkId}/preference", preferenceDto, Long.MAX_VALUE)
                .withDocument("member/preference/create-fail-drink")
                .withUser(CROFFLE)
                .build();

        //then
        예외_검증(httpResponse.errorResponse(), NOT_FOUND_DRINK);
    }

    @Test
    @DisplayName("선호도 삭제 - 성공")
    public void deletePreference() {
        //given
        final Long obId = 주류_등록(OB);
        memberAcceptanceTool.선호도_등록(obId, 3.5, CROFFLE);

        //when
        final HttpResponse httpResponse = request()
                .delete("/members/me/drinks/{drinkId}/preference", obId)
                .withDocument("member/preference/delete")
                .withUser(CROFFLE)
                .build();

        //then
        final DrinkResponse drinkResponse = drinkAcceptanceTool.단일_상품_조회(obId);

        assertThat(httpResponse.statusCode()).isEqualTo(HttpStatus.OK);
        assertThat(drinkResponse.getPreferenceAvg()).isEqualTo(0.0);
    }

    @Test
    @DisplayName("선호도 삭제 - 실패(비로그인 유저가 선호도 등록 및 수정 요청했을 때)")
    public void deletePreference_fail_unauthorizedUser() {
        //given

        final Long obId = 주류_등록(OB);
        memberAcceptanceTool.선호도_등록(obId, 3.5, CROFFLE);

        //when
        final HttpResponse httpResponse = request()
                .delete("/members/me/drinks/{drinkId}/preference", obId)
                .withDocument("member/preference/delete-fail-user")
                .build();

        //then
        예외_검증(httpResponse.errorResponse(), UNAUTHORIZED_USER);
    }

    private HttpResponse 내_정보를_수정한다(TestMember testMember,
            MemberRequest memberRequest) {
        return 내_정보를_수정한다(testMember, memberRequest, "");
    }

    private HttpResponse 내_정보를_수정한다(TestMember testMember,
            MemberRequest memberRequest,
            String documentPath) {
        RequestOption updateRequest = request().put("/members/me", memberRequest);

        if (!documentPath.isBlank()) {
            updateRequest.withDocument(documentPath);
        }

        return updateRequest.withUser(testMember)
                .build();
    }

    private MemberResponse 내_정보를_조회한다(TestMember testMember) {
        return 내_정보를_조회한다("", testMember);
    }

    private MemberResponse 내_정보를_조회한다(String documentPath, TestMember testMember) {
        RequestOption getRequest = request().get("/members/me");
        if (!documentPath.isBlank()) {
            getRequest.withDocument(documentPath);
        }
        return getRequest.withUser(testMember)
                .build()
                .convertBody(MemberResponse.class);
    }

    private Long 주류_등록(DrinkTestContainer drinkTestContainer) {
        adminAcceptanceTool.어드민_주류_데이터_등록(drinkTestContainer);
        return drinkAcceptanceTool.주류_아이디_조회(drinkTestContainer.getName());
    }
}
