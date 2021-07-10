package com.jujeol.drink.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.jujeol.AcceptanceTest;
import com.jujeol.commons.dto.CommonResponseDto;
import com.jujeol.drink.application.dto.DrinkResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class DrinkAcceptanceTest extends AcceptanceTest {

    @DisplayName("전체 조회 - 성공")
    @Test
    public void showDrinksTest() {
        //given
        //when
        ExtractableResponse<Response> response = request()
                .get("/drinks")
                .withDocument("drinks/show/all")
                .build();

        CommonResponseDto<List<DrinkResponse>> expectedResult =
                response.body().as(CommonResponseDto.class);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(expectedResult.getData().size()).isEqualTo(7);
    }
}
