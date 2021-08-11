package com.jujeol.commons.exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jujeol.drink.drink.exception.InvalidAlcoholByVolumeException;
import com.jujeol.drink.drink.exception.InvalidDrinkNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommonsExceptionTest {

    @DisplayName("예외 검색 - 성공")
    @Test
    void findByTest() {
        //given
        InvalidAlcoholByVolumeException invalidAlcoholByVolumeException = new InvalidAlcoholByVolumeException();
        InvalidDrinkNameException invalidDrinkNameException = new InvalidDrinkNameException();

        //when
        String invalidAlcoholByVolumeExceptionCode = invalidAlcoholByVolumeException.getCode();
        String invalidAlcoholByVolumeExceptionMessage = invalidAlcoholByVolumeException
                .getMessage();

        String invalidDrinkNameExceptionCode = invalidDrinkNameException.getCode();
        String invalidDrinkNameExceptionMessage = invalidDrinkNameException.getMessage();

        //then
        assertThat(invalidAlcoholByVolumeExceptionCode)
                .isEqualTo(ExceptionCodeAndDetails.INVALID_ALCOHOL_BY_VOLUME.getCode());
        assertThat(invalidAlcoholByVolumeExceptionMessage)
                .isEqualTo(ExceptionCodeAndDetails.INVALID_ALCOHOL_BY_VOLUME.getMessage());

        assertThat(invalidDrinkNameExceptionCode)
                .isEqualTo(ExceptionCodeAndDetails.INVALID_DRINK_NAME.getCode());
        assertThat(invalidDrinkNameExceptionMessage)
                .isEqualTo(ExceptionCodeAndDetails.INVALID_DRINK_NAME.getMessage());
    }

    @DisplayName("예외 검색 -> 실패")
    @Test
    void failFoundExceptionTest() {
        //given
        //when
        //then
        assertThatThrownBy(
                JujeolException::new) // 상속 객체가 없으면 ExceptionCodeAndDetails 에서 조회가 되지 않는다.
                .isInstanceOf(NotFoundErrorCodeException.class);
    }
}