package com.jujeol.drink.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ImageFilePathTest {

    @DisplayName("이미지 파일 URL 생성")
    @Test
    void splitImageUrlTest() {
        //given
        String rawImageUrl = "logo.png";
        //when
        ImageFilePath imageFilePath = new ImageFilePath(rawImageUrl);
        //then
        assertThat(imageFilePath.getImageFilePath()).isEqualTo("logo.png");
    }
}
