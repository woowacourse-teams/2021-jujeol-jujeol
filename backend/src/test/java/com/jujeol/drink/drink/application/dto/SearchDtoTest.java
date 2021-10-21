package com.jujeol.drink.drink.application.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.jujeol.elasticsearch.application.dto.SearchDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SearchDtoTest {

    @DisplayName("검색 DTO 생성 - 성공")
    @Test
    void searchDtoCreate() {
        //given
        SearchDto searchDto = SearchDto.create("맥주");
        //when
        //then
        assertThat(searchDto.getKeyword()).isEqualTo("맥주");
    }
}