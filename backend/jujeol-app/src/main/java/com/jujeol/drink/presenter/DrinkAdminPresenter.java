package com.jujeol.drink.presenter;

import com.jujeol.commons.exception.JujeolBadRequestException;
import com.jujeol.drink.controller.requeset.AdminDrinkSaveRequest;
import com.jujeol.drink.domain.exception.NotFoundCategoryException;
import com.jujeol.drink.domain.model.ImageFilePath;
import com.jujeol.drink.service.DrinkService;
import com.jujeol.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class DrinkAdminPresenter {

    private final ImageService imageService;
    private final DrinkService drinkService;

    public void saveDrink(AdminDrinkSaveRequest adminDrinkSaveRequest, MultipartFile image) {
        ImageFilePath imageFilePath = imageService.save(image);
        try {
            drinkService.saveDrink(adminDrinkSaveRequest, imageFilePath);
        } catch (NotFoundCategoryException e) {
            throw new JujeolBadRequestException();
        }
    }
}
