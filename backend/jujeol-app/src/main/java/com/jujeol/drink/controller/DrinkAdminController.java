package com.jujeol.drink.controller;

import com.jujeol.commons.CommonResponse;
import com.jujeol.drink.controller.requeset.AdminDrinkSaveRequest;
import com.jujeol.drink.presenter.DrinkAdminPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class DrinkAdminController {

    private final DrinkAdminPresenter drinkAdminPresenter;

    @PostMapping("/drinks")
    public CommonResponse<?> saveDrinks(
        @ModelAttribute AdminDrinkSaveRequest adminDrinkSaveRequest,
        @RequestPart MultipartFile image
    ) {
        drinkAdminPresenter.saveDrink(adminDrinkSaveRequest, image);
        return CommonResponse.ok();
    }
}
