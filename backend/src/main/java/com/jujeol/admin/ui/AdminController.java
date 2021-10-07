package com.jujeol.admin.ui;

import com.jujeol.admin.ui.dto.AdminDrinkRequest;
import com.jujeol.admin.ui.dto.AdminDrinkResponse;
import com.jujeol.aws.service.ImageService;
import com.jujeol.commons.dto.CommonResponse;
import com.jujeol.commons.dto.PageResponseAssembler;
import com.jujeol.drink.drink.application.DrinkService;
import com.jujeol.drink.drink.application.dto.DrinkDto;
import com.jujeol.drink.drink.application.dto.ImageFilePathDto;
import com.jujeol.member.auth.ui.AuthenticationPrincipal;
import com.jujeol.member.auth.ui.LoginMember;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final DrinkService drinkService;
    private final ImageService imageService;

    @GetMapping("/drinks")
    public CommonResponse<List<AdminDrinkResponse>> showDrinks(
        @PageableDefault(value = 20, sort = "id", direction = Direction.DESC) Pageable pageable,
        @AuthenticationPrincipal LoginMember loginMember) {
        final Page<AdminDrinkResponse> drinks = drinkService
            .showAllDrinksByPage(pageable, loginMember)
            .map(AdminDrinkResponse::from);
        return PageResponseAssembler.assemble(drinks);
    }

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
