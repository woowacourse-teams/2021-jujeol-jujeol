package com.jujeol.admin.ui;

import com.jujeol.admin.ui.dto.AdminDrinkRequest;
import com.jujeol.admin.ui.dto.AdminDrinkResponse;
import com.jujeol.commons.dto.CommonResponse;
import com.jujeol.commons.dto.PageResponseAssembler;
import com.jujeol.drink.drink.application.DrinkService;
import com.jujeol.drink.drink.application.dto.DrinkRequestDto;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final DrinkService drinkService;

    @GetMapping("/drinks")
    public CommonResponse<List<AdminDrinkResponse>> showDrinks(
            @PageableDefault(value = 20, sort = "id", direction = Direction.DESC) Pageable pageable) {
        final Page<AdminDrinkResponse> drinks = drinkService.showAllDrinksByPage(pageable)
                .map(AdminDrinkResponse::from);
        return PageResponseAssembler.assemble(drinks);
    }

    @PostMapping("/drinks")
    public CommonResponse<?> insertDrinks(@ModelAttribute AdminDrinkRequest adminDrinkRequest) {
        final DrinkRequestDto drinkRequest = adminDrinkRequest.toDto();
        drinkService.insertDrinks(drinkRequest);
        return CommonResponse.ok();
    }

    @PutMapping("/drinks/{id}")
    public CommonResponse<?> updateDrink(@PathVariable Long id,
            @RequestBody AdminDrinkRequest adminDrinkRequest) {
        drinkService.updateDrink(id, adminDrinkRequest.toDto());
        return CommonResponse.ok();
    }

    @DeleteMapping("/drinks/{id}")
    public CommonResponse<?> removeDrink(@PathVariable Long id) {
        drinkService.removeDrink(id);
        return CommonResponse.ok();
    }
}
