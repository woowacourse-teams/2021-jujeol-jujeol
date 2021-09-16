package com.jujeol.admin.ui;

import com.jujeol.admin.ui.dto.AdminDrinkRequest;
import com.jujeol.admin.ui.dto.AdminDrinkResponse;
import com.jujeol.aws.service.ImageResizerImpl.ImageSize;
import com.jujeol.aws.service.ImageService;
import com.jujeol.commons.dto.CommonResponse;
import com.jujeol.commons.dto.PageResponseAssembler;
import com.jujeol.drink.drink.application.DrinkService;
import java.util.EnumMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final DrinkService drinkService;
    private final ImageService imageService;

    @GetMapping("/drinks")
    public CommonResponse<List<AdminDrinkResponse>> showDrinks(
            @PageableDefault(value = 20, sort = "id", direction = Direction.DESC) Pageable pageable) {
        final Page<AdminDrinkResponse> drinks = drinkService.showAllDrinksByPage(pageable)
                .map(AdminDrinkResponse::from);
        return PageResponseAssembler.assemble(drinks);
    }

    @PostMapping("/drinks")
    public CommonResponse<?> insertDrinks(@ModelAttribute AdminDrinkRequest adminDrinkRequest) {
        final EnumMap<ImageSize, String> imageUrls = imageService.insert(adminDrinkRequest.getImage());
        drinkService.insertDrink(adminDrinkRequest.toDto(
                imageUrls.get(ImageSize.SMALL),
                imageUrls.get(ImageSize.MEDIUM),
                imageUrls.get(ImageSize.LARGE)
        ));
        return CommonResponse.ok();
    }

    @PutMapping("/drinks/{id}")
    public CommonResponse<?> updateDrink(@PathVariable Long id,
            @RequestBody AdminDrinkRequest adminDrinkRequest) {
        final EnumMap<ImageSize, String> imageUrls = imageService.insert(adminDrinkRequest.getImage());
        drinkService.updateDrink(id, adminDrinkRequest.toDto(
                imageUrls.get(ImageSize.SMALL),
                imageUrls.get(ImageSize.MEDIUM),
                imageUrls.get(ImageSize.LARGE)
        ));
        return CommonResponse.ok();
    }

    @DeleteMapping("/drinks/{id}")
    public CommonResponse<?> removeDrink(@PathVariable Long id) {
        drinkService.removeDrink(id);
        return CommonResponse.ok();
    }
}
