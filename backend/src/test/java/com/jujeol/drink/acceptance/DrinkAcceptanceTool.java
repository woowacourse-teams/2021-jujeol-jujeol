package com.jujeol.drink.acceptance;

import com.jujeol.RequestBuilder;
import com.jujeol.commons.exception.JujeolExceptionDto;
import com.jujeol.drink.domain.repository.DrinkRepository;
import com.jujeol.drink.exception.NotFoundDrinkException;
import com.jujeol.drink.ui.dto.DrinkDetailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

@Component
@ActiveProfiles("test")
public class DrinkAcceptanceTool {

    @Autowired
    private RequestBuilder requestBuilder;
    @Autowired
    private DrinkRepository drinkRepository;

    public DrinkDetailResponse 단일_상품_조회(Long id) {
        return requestBuilder.builder().get("/drinks/{id}", id).withoutLog().build().convertBody(DrinkDetailResponse.class);
    }

    public JujeolExceptionDto 단일_상품_조회_실패(Long id) {
        return requestBuilder.builder().get("/drinks/{id}", id).withoutLog().build().errorResponse();
    }

    public Long 주류_아이디_조회(String drinkName) {
        return drinkRepository.findByName(drinkName).orElseThrow(NotFoundDrinkException::new).getId();
    }
}
