package com.jujeol.drink.drink.application.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SearchDtoTest {

    @DisplayName("검색 DTO 생성 - 성공")
    @Test
    void searchDtoCreate() {
        //given
        SearchDto searchDto = SearchDto.create("맥주", "BEER");
        //when
        //then
        assertThat(searchDto.getKeyword()).isEqualTo("맥주");
        assertThat(searchDto.getCategoryKey()).isEqualTo("BEER");
    }
}