package com.jujeol.drink.acceptance;

import static com.jujeol.TestDataLoader.BEERS;
import static org.assertj.core.api.Assertions.assertThat;

import com.jujeol.AcceptanceTest;
import com.jujeol.commons.dto.CommonResponseDto;
import com.jujeol.drink.application.dto.DrinkResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DrinkAcceptanceTest extends AcceptanceTest {

    @DisplayName("전체 조회 - 성공")
    @Test
    public void showDrinks() {
        CommonResponseDto<List<DrinkResponse>> expectedResult =
                request()
                        .get("/drinks")
                        .withDocument("drinks/show/all")
                        .build()
                        .body()
                        .as(CommonResponseDto.class);

        List<DrinkResponse> drinkResponses = BEERS.stream()
                .filter(drink -> drink.getId() < 8)
                .map(drink -> DrinkResponse.from(drink, ""))
                .collect(Collectors.toList());

        List<DrinkResponse> data = expectedResult.getData();
//        assertThat(data).usingRecursiveComparison()
//        assertThat(data).containsAll(drinkResponses);

        assertThat(data.size()).isEqualTo(7);
    }
}
