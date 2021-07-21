package com.jujeol.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jujeol.member.exception.InvalidUserBiographyLengthException;
import com.jujeol.member.exception.InvalidUserNicknameLengthException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class BiographyTest {

    @DisplayName("자기소개 생성 - 성공(10글자 초과 혹은 0자)")
    @ParameterizedTest
    @ValueSource(strings = {"열글자가되는소개짜잔스무글자되는소개짜잔서른글자되는소개짜잔여섯글자더함"})
    void createBiography_fail_InvalidNicknameLength(String biography) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new Biography(biography))
                .isInstanceOf(InvalidUserBiographyLengthException.class);
    }

    @DisplayName("자기소개 생성 - 성공")
    @ParameterizedTest
    @ValueSource(strings = {"","적절한자기소개", "열글자가되는소개짜잔스무글자되는소개짜잔서른글자되는소개짜잔다섯글자더"})
    void createBiography(String biography) {
        //given
        //when
        //then
        assertThat(new Biography(biography)).isNotNull();
    }
}