package com.jujeol.admin.ui;

import com.jujeol.admin.ui.dto.AdminDrinkResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final DrinkService drinkService;


    @GetMapping("/drinks")
    public void drinks(Pageable pageable) {
        Page<AdminDrinkResponse> adminDrinkResponses = drinkService.findDrinks(pageable);
    }
}
