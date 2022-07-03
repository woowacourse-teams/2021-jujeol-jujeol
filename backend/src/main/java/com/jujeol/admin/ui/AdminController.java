package com.jujeol.admin.ui;

import com.jujeol.admin.ui.dto.AdminDrinkRequest;
import com.jujeol.aws.service.ImageService;
import com.jujeol.commons.dto.CommonResponse;
import com.jujeol.drink.drink.application.DrinkService;
import com.jujeol.drink.drink.application.dto.DrinkDto;
import com.jujeol.drink.drink.application.dto.ImageFilePathDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final DrinkService drinkService;
    private final ImageService imageService;

    @PostMapping("/drinks")
    public CommonResponse<?> insertDrinks(
        @ModelAttribute AdminDrinkRequest adminDrinkRequest,
        @RequestPart MultipartFile image
    ) {
        ImageFilePathDto imageFilePathDto = imageService.insert(image);
        drinkService.insertDrink(adminDrinkRequest.toDto(
            imageFilePathDto
        ));
        return CommonResponse.ok();
    }

    @PutMapping("/drinks/{id}")
    public CommonResponse<?> updateDrink(@PathVariable Long id,
        @ModelAttribute AdminDrinkRequest adminDrinkRequest,
        @RequestPart(required = false) MultipartFile image
    ) {
        DrinkDto drinkDto = drinkService.showDrinkDetail(id);
        ImageFilePathDto imageFilePathDto = drinkDto.getImageFilePathDto();

        if (Objects.nonNull(image)) {
            imageFilePathDto = imageService.update(imageFilePathDto, image);
        }

        drinkService.updateDrink(id, adminDrinkRequest.toDto(
            imageFilePathDto
        ));
        return CommonResponse.ok();
    }

    @DeleteMapping("/drinks/{id}")
    public CommonResponse<?> removeDrink(@PathVariable Long id) {
        drinkService.removeDrink(id);
        return CommonResponse.ok();
    }
}
