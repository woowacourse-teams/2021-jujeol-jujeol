package com.jujeol.drink.presenter;

import com.jujeol.drink.controller.requeset.AdminDrinkSaveRequest;
import com.jujeol.drink.controller.response.AdminDrinkResponse;
import com.jujeol.drink.domain.exception.NotFoundCategoryException;
import com.jujeol.drink.domain.model.ImageFilePath;
import com.jujeol.drink.service.DrinkService;
import com.jujeol.exception.ExceptionCodeAndDetails;
import com.jujeol.exception.JujeolBadRequestException;
import com.jujeol.image.service.ImageService;
import com.jujeol.member.resolver.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            throw new JujeolBadRequestException(ExceptionCodeAndDetails.NOT_EXIST_CATEGORY);
        }
    }

    // TODO : Page, pageable 관련 제거 필요
    public Page<AdminDrinkResponse> showAdminDrinks(Pageable pageable, LoginMember loginMember) {
        // TODO : 어드민 권한 관련 확인 필요

        return drinkService.getDrinksPage(pageable)
            .map(drink ->
                AdminDrinkResponse.builder()
                    .id(drink.getDrinkId())
                    .name(drink.getName().value())
                    .englishName(drink.getEnglishName().value())
                    .alcoholByVolume(drink.getAlcoholByVolume().value())
                    .imageUrl(AdminDrinkResponse.ImageUrlResponse.create(
                        drink.getImageFilePath().getSmallImageFilePath(),
                        drink.getImageFilePath().getMediumImageFilePath(),
                        drink.getImageFilePath().getLargeImageFilePath()
                    ))
                    .category(AdminDrinkResponse.CategoryResponse.create(
                        drink.getCategory().getId(),
                        drink.getCategory().getName(),
                        drink.getCategory().getKey()
                    ))
                    .description(drink.getDescription().value())
                    .build()
            );
    }
}
