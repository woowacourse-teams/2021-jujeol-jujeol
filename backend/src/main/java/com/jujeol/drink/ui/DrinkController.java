package com.jujeol.drink.ui;

import com.jujeol.commons.dto.CommonResponseDto;
import com.jujeol.drink.application.DrinkService;
import com.jujeol.drink.application.dto.DrinkDetailResponse;
import com.jujeol.drink.application.dto.DrinkSimpleResponse;
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
    public ResponseEntity<CommonResponseDto<List<DrinkSimpleResponse>>> showDrinks() {
        List<DrinkSimpleResponse> drinkSimpleResponse = drinkService.showDrinks();
        return ResponseEntity.ok(CommonResponseDto.fromList(drinkSimpleResponse, drinkSimpleResponse.size()));
    }

    @GetMapping("/drinks/{id}")
    public ResponseEntity<CommonResponseDto<DrinkDetailResponse>> showDrinkDetail(@PathVariable Long id) {
        // TODO: member & preference 추가
        DrinkDetailResponse drinkResponse = drinkService.showDrinkDetail(id);
        return ResponseEntity.ok(CommonResponseDto.fromOne(drinkResponse));
    }
}
