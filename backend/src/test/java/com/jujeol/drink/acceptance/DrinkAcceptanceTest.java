package com.jujeol.drink.acceptance;

import static com.jujeol.TestDataLoader.BEERS;
import static org.assertj.core.api.Assertions.assertThat;

import com.jujeol.AcceptanceTest;
import com.jujeol.drink.application.dto.DrinkResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DrinkAcceptanceTest extends AcceptanceTest {

    @DisplayName("전체 조회 - 성공")
    @Test
    public void showDrinksTest() {
        //given
        //when
        List<DrinkResponse> drinkResponses = request()
                .get("/drinks")
                .withDocument("drinks/show/all")
                .build().convertBodyToList(DrinkResponse.class);

        //then
        List<DrinkResponse> expectedResult = BEERS.stream()
                .filter(drink -> drink.getId() < 8)
                .map(drink -> DrinkResponse.from(drink, ""))
                .collect(Collectors.toList());

        assertThat(expectedResult).containsAll(drinkResponses);
    }
}