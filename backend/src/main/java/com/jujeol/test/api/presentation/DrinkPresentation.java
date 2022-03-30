package com.jujeol.test.api.presentation;

import com.jujeol.test.api.service.DrinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DrinkPresentation {

    // 화면에 핏하게

    private DrinkService drinkService;

    void registerDrink(String drinkDto) {
        if(drinkDto.equals("id")) {
            throw new IllegalStateException("이미 드링크가 존재합니다.");
        }
        drinkService.registerDrink(drinkDto.serviceModel());
    }

    void changeName(String name) {
        if (name.equals("이미 존재")) {
            throw new IllegalStateException("중복된 이름입니다.");
        }

        drinkService.changeName(name);
    }
}
