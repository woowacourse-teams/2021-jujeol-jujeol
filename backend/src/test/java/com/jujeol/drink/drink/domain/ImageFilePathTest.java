package com.jujeol.drink.drink.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ImageFilePathTest {

    @DisplayName("이미지 파일 URL 생성")
    @Test
    void splitImageUrlTest() {
        //given
        String smallImageUrl = "https://test/w_200/logo_w200.png";
        String mediumImageUrl = "https://test/w_400/logo_w400.png";
        String largeImageUrl = "https://test/w_600/logo_w600.png";
        //when
        ImageFilePath imageFilePath = ImageFilePath.create(smallImageUrl, mediumImageUrl, largeImageUrl);
        //then
        assertThat(imageFilePath.getSmallImageFilePath()).isEqualTo("https://test/w_200/logo_w200.png");
        assertThat(imageFilePath.getMediumImageFilePath()).isEqualTo("https://test/w_400/logo_w400.png");
        assertThat(imageFilePath.getLargeImageFilePath()).isEqualTo("https://test/w_600/logo_w600.png");
    }
}
