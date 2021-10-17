package com.jujeol.drink.acceptance;

import com.jujeol.commons.exception.JujeolExceptionDto;
import com.jujeol.drink.drink.domain.repository.DrinkRepository;
import com.jujeol.drink.drink.exception.NotFoundDrinkException;
import com.jujeol.drink.drink.ui.dto.DrinkResponse;
import com.jujeol.testtool.NewRequestBuilder;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

@Component
@ActiveProfiles("test")
public class DrinkAcceptanceTool {

    public static final File TEST_IMAGE = new File(new File("").getAbsolutePath() + "/src/test/resources/static/test.png");

    public static MockMultipartFile TEST_MULTIPART;

    static {
        try {
            TEST_MULTIPART = new MockMultipartFile(
                TEST_IMAGE.getAbsolutePath(),
                TEST_IMAGE.getName(),
                "image/png",
                Files.readAllBytes(TEST_IMAGE.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private NewRequestBuilder requestBuilder;
    @Autowired
    private DrinkRepository drinkRepository;

    public DrinkResponse 단일_상품_조회(Long id) {
        return requestBuilder.builder().get("/drinks/{id}", id).withoutLog().build()
                .convertBody(DrinkResponse.class);
    }

    public JujeolExceptionDto 단일_상품_조회_실패(Long id) {
        return requestBuilder.builder().get("/drinks/{id}", id).withoutLog().build()
                .errorResponse();
    }

    public Long 주류_아이디_조회(String drinkName) {
        return drinkRepository.findByName(drinkName).orElseThrow(NotFoundDrinkException::new)
                .getId();
    }
}
