package com.jujeol.admin.ui;

import com.jujeol.admin.ui.dto.AdminDrinkRequest;
import com.jujeol.admin.ui.dto.AdminDrinkResponse;
import com.jujeol.commons.dto.CommonResponse;
import com.jujeol.commons.dto.PageResponseAssembler;
import com.jujeol.drink.application.DrinkService;
import com.jujeol.drink.application.dto.AdminDrinkRequestDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/drinks")
    public CommonResponse<List<AdminDrinkResponse>> showDrinks(Pageable pageable) {
        final Page<AdminDrinkResponse> drinks = drinkService.showDrinks(pageable)
                .map(AdminDrinkResponse::from);
        return PageResponseAssembler.assemble(drinks);
    }

    @PostMapping("/drinks")
    public CommonResponse<?> insertDrinks(@RequestBody List<AdminDrinkRequest> adminDrinkRequests) {
        final List<AdminDrinkRequestDto> drinkRequests = adminDrinkRequests.stream()
                .map(AdminDrinkRequest::toDto).collect(Collectors.toList());
        drinkService.insertDrinks(drinkRequests);
        return CommonResponse.ok();
    }

    @PutMapping("/drinks/{id}")
    public CommonResponse<?> updateDrink(@PathVariable Long id, @RequestBody AdminDrinkRequest adminDrinkRequest) {
        drinkService.updateDrink(id, adminDrinkRequest.toDto());
        return CommonResponse.ok();
    }

    @DeleteMapping("/drinks/{id}")
    public CommonResponse<?> removeDrink(@PathVariable Long id) {
        drinkService.removeDrink(id);
        return CommonResponse.ok();
    }
}
