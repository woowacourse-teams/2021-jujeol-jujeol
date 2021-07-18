package com.jujeol.drink.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jujeol.drink.exception.InvalidEnglishNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DrinkEnglishNameTest {

    @DisplayName("주류 영어 이름 - 성공")
    @ParameterizedTest
    @ValueSource(strings = {"stella", "Tiger_Rad", "Tiger_Rad", "gom_pyo", "TSINGTAO", "gom_pyo",
            "OB", "Tiger_Lemon"})
    void drinkEnglishNameTest(String englishName) {
        //given
        DrinkEnglishName drinkEnglishName = new DrinkEnglishName(englishName);
        //when
        //then
        assertThat(drinkEnglishName.getEnglishName()).isEqualTo(englishName);
    }

    @DisplayName("주류 영어 이름 - 실패(형식에 맞지 않은 이름)")
    @ParameterizedTest
    @ValueSource(strings = {"소롱", "피카", "크로플", "맥주는 바보"})
    void drinkEnglishNameTest_fail(String englishName) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new DrinkEnglishName(englishName))
                .isInstanceOf(InvalidEnglishNameException.class);
    }
}
