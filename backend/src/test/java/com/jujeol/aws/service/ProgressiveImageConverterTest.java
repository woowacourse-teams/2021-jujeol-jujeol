package com.jujeol.aws.service;


import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.net.URL;
import org.apache.tika.Tika;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ProgressiveImageConverterTest {

    private final ProgressiveImageConverter progressiveImageConverter = new ProgressiveImageConverter(new Tika());

    @DisplayName("jpeg파일 지원 기능 확인")
    @ParameterizedTest
    @CsvSource({"static/test.png, false", "static/test.jpeg, true"})
    void supportTest(String filePath, boolean expectedResult) {
        //given

        URL resource = ProgressiveImageConverterTest.class.getClassLoader()
            .getResource(filePath);
        assert resource != null;
        File file = new File(resource.getPath());
        //when
        //then
        assertThat(progressiveImageConverter.isSupport(file)).isEqualTo(expectedResult);
    }

}