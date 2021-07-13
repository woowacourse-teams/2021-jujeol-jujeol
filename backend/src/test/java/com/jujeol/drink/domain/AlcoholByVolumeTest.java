package com.jujeol.drink.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.jujeol.drink.exception.InvalidAlcoholByVolumeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class AlcoholByVolumeTest {

    @DisplayName("도수 생성 - 성공")
    @ParameterizedTest
    @NullSource
    @ValueSource(doubles = {0.0, 10.0, 18.5, 20.123213, 30.0, 45.0, 56.0, 99.0, 100.0})
    void alcoholByVolumeTest(Double abv) {
        //given
        AlcoholByVolume alcoholByVolume = new AlcoholByVolume(abv);
        //when
        //then
        assertThat(alcoholByVolume.getValue())
                .isEqualTo(abv);
    }

    @DisplayName("도수 생성 - 실패(올바르지 않은 범위)")
    @ParameterizedTest
    @ValueSource(doubles = {-0.1, 100.1})
    void alcoholByVolumeTest_fail_invalidAlcoholByVolume(double abv) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new AlcoholByVolume(abv))
                .isInstanceOf(InvalidAlcoholByVolumeException.class);
    }
}
