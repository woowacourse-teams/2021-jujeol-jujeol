package com.jujeol.drink.acceptance;

import static com.jujeol.TestDataLoader.BEERS;
import static com.jujeol.TestDataLoader.PREFERENCE;
import static org.assertj.core.api.Assertions.assertThat;

import com.jujeol.AcceptanceTest;
import com.jujeol.RequestBuilder.HttpResponse;
import com.jujeol.commons.exception.ExceptionCodeAndDetails;
import com.jujeol.commons.exception.JujeolExceptionDto;
import com.jujeol.drink.application.dto.DrinkDto;
import com.jujeol.drink.ui.dto.DrinkDetailResponse;
import com.jujeol.drink.ui.dto.DrinkSimpleResponse;
import com.jujeol.member.domain.Preference;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class DrinkAcceptanceTest extends AcceptanceTest {

    @DisplayName("전체 조회 - 성공")
    @Test
    public void showDrinksTest() {
        //given
        //when
        final HttpResponse httpResponse = request()
                .get("/drinks")
                .withDocument("drinks/show/all")
                .build();

        //then
        List<DrinkSimpleResponse> expectedResult = BEERS.stream()
                .filter(drink -> drink.getId() < 8)
                .map(drink -> DrinkDto.create(drink, Preference.from(drink, 0), ""))
                .map(DrinkSimpleResponse::from)
                .collect(Collectors.toList());

        final List<DrinkSimpleResponse> drinkSimpleResponses =
                httpResponse.convertBodyToList(DrinkSimpleResponse.class);

        assertThat(drinkSimpleResponses).usingRecursiveComparison()
                .isEqualTo(expectedResult);

        페이징_검증(httpResponse.pageInfo(), 1, 2, 7, 8);
    }

    @DisplayName("추천 조회(선호도) - 성공")
    @Test
    public void showDrinksByPreferenceTest() {
        //given
        String theme = "preference";

        //when
        List<DrinkSimpleResponse> drinkSimpleResponses = request()
                .get("/drinks?theme=" + theme + "&page=1")
                .withDocument("drinks/show/all-theme")
                .build().convertBodyToList(DrinkSimpleResponse.class);

        //then
        List<DrinkSimpleResponse> expectedResult = BEERS.stream()
                .map(drink -> DrinkDto.create(drink, Preference.from(drink, 0), ""))
                .map(DrinkSimpleResponse::from)
                .collect(Collectors.toList());

        assertThat(drinkSimpleResponses).usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @DisplayName("추천 조회(조회수) - 성공")
    @Test
    public void showDrinksByViewCountTest() {
        //given
        String theme = "view-count";
        request()
                .get("/drinks/1")
                .build();

        //when
        List<DrinkSimpleResponse> drinkSimpleResponses = request()
                .get("/drinks?theme=" + theme + "&page=1")
                .build().convertBodyToList(DrinkSimpleResponse.class);

        //then

        List<DrinkSimpleResponse> expectedResult = BEERS.stream()
                .filter(drink -> drink.getId() == 1)
                .map(drink -> DrinkDto.create(drink, Preference.from(drink, 0), ""))
                .map(DrinkSimpleResponse::from)
                .collect(Collectors.toList());

        assertThat(drinkSimpleResponses).usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @DisplayName("단일 조회 - 성공")
    @Test
    public void showDrinkDetailTest() {
        //given
        //when
        DrinkDetailResponse drinkDetailResponse = request()
                .get("/drinks/1")
                .withDocument("drinks/show/detail")
                .withUser()
                .build()
                .convertBody(DrinkDetailResponse.class);

        //then
        DrinkDetailResponse expectedResult = DrinkDetailResponse.from(
                DrinkDto.create(BEERS.get(0), PREFERENCE, "")
        );

        assertThat(expectedResult).isEqualTo(drinkDetailResponse);
    }

    @DisplayName("단일 조회 - 실패 (찾을 수 없는 id)")
    @Test
    public void showDrinkDetailTest_fail() {
        //given
        //when
        ExtractableResponse<Response> response = request()
                .get("drinks/1000")
                .withDocument("drinks/show/detail-fail")
                .build().totalResponse();

        JujeolExceptionDto body = response.body().as(JujeolExceptionDto.class);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(body.getCode()).isEqualTo(ExceptionCodeAndDetails.NOT_FOUND_DRINK.getCode());
        assertThat(body.getMessage())
                .isEqualTo(ExceptionCodeAndDetails.NOT_FOUND_DRINK.getMessage());
    }
}
