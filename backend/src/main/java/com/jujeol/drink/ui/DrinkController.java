package com.jujeol.drink.ui;

import com.jujeol.commons.dto.CommonResponseDto;
import com.jujeol.drink.application.DrinkService;
import com.jujeol.drink.application.dto.DrinkResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class DrinkController {

    private final DrinkService drinkService;

    @GetMapping("/drinks")
    public ResponseEntity<CommonResponseDto<List<DrinkResponse>>> showDrinks() {
        List<DrinkResponse> drinkResponses = drinkService.showDrinks();
        return ResponseEntity.ok(CommonResponseDto.fromList(drinkResponses, drinkResponses.size()));
    }

    @GetMapping("/drinks/{id}")
    public ResponseEntity<CommonResponseDto<DrinkResponse>> showDrinkDetail(@PathVariable Long id) {
        DrinkResponse drinkResponse = drinkService.showDrinkDetail(id);
        return ResponseEntity.ok(CommonResponseDto.fromOne(drinkResponse));
    }
}
