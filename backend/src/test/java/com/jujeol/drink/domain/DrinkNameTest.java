package com.jujeol.drink.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jujeol.drink.exception.InvalidDrinkNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class DrinkNameTest {

    @DisplayName("이름 생성 - 성공")
    @Test
    void drinkNameTest() {
        //given
        DrinkName drinkName = new DrinkName("오비맥주");
        //when
        //then
        assertThat(drinkName.getName()).isEqualTo("오비맥주");
    }

    @DisplayName("이름 생성 - 실패")
    @ParameterizedTest
    @NullAndEmptySource
    void drinkNameTest_fail_InvalidDrinkName(String emptyOrNull) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new DrinkName(emptyOrNull))
                .isInstanceOf(InvalidDrinkNameException.class);
    }
}
