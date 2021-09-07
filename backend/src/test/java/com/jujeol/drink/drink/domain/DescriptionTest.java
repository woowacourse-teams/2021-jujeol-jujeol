package com.jujeol.drink.drink.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jujeol.drink.drink.exception.InvalidDescriptionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class DescriptionTest {

    @DisplayName("상세설명 생성 - 성공")
    @Test
    void descriptionTest() {
        //given
        String descriptionString = "이 주류는 달콤한 향이 특징이고, 과일향이 나는 과실주 입니다.";
        //when
        Description description = new Description(descriptionString);
        //then

        assertThat(description.getDescription()).isEqualTo(descriptionString);
    }

    @DisplayName("상세설명 생성 - 실패(유효하지 않는 글자 수)")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "  ",
            "이 내용은 글자수 1001자가 넘는 글자입니다. 글자는 글자글자 글자는 1000자이 내용은 글자수 1000자가 넘는 글자입니다. 글자는 글자글자 글자는 1000자이 내용은 글자수 1000자가 넘는 글자입니다. 글자는 글자글자 글자는 1000자이 내용은 글자수 1000자가 넘는 글자입니다. 글자는 글자글자 글자는 1000자이 내용은 글자수 1000자가 넘는 글자입니다. 글자는 글자글자 글자는 1000자이 내용은 글자수 1000자가 넘는 글자입니다. 글자는 글자글자 글자는 1000자이 내용은 글자수 1000자가 넘는 글자입니다. 글자는 글자글자 글자는 1000자이 내용은 글자수 1000자가 넘는 글자입니다. 글자는 글자글자 글자는 1000자이 내용은 글자수 1000자가 넘는 글자입니다. 글자는 글자글자 글자는 1000자이 내용은 글자수 1000자가 넘는 글자입니다. 글자는 글자글자 글자는 1000자이 내용은 글자수 1000자가 넘는 글자입니다. 글자는 글자글자 글자는 1000자이 내용은 글자수 1000자가 넘는 글자입니다. 글자는 글자글자 글자는 1000자이 내용은 글자수 1000자가 넘는 글자입니다. 글자는 글자글자 글자는 1000자이 내용은 글자수 1000자가 넘는 글자입니다. 글자는 글자글자 글자는 1000자이 내용은 글자수 1000자가 넘는 글자입니다. 글자는 글자글자 글자는 1000자이 내용은 글자수 1000자가 넘는 글자입니다. 글자는 글자글자 글자는 1000자이 내용은 글자수 1000자가 넘는 글자입니다. 글자는 글자글자 글자는 1000자이 내용은 글자수 1000자가 넘는 글자입니다. 글자는 글자글자 글자는 1000자이 내용은 글자수 1000자가 넘는 글자입니다. 글자는 글자글자 글자는 1000자이 내용은 글자수 1000자가 넘는 글자입니다. 글자는 글자글자 글자는 1000자이 내용은 글자수 1000자가 넘는 글자입니다. 글자는 글자글자 글자는 1000자이 내용은 글자수 1000자가 넘는 글자입니다. 글자는 글자글자 글자는 1000자.1001자까지하나1"})
    void descriptionFailTest(String descriptionString) {
        assertThatThrownBy(() -> new Description(descriptionString))
                .isInstanceOf(InvalidDescriptionException.class);
    }
}