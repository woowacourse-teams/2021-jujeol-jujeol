package com.jujeol.drink.ui;

import com.jujeol.admin.PageResponseAssembler;
import com.jujeol.admin.ui.dto.AdminDrinkResponse;
import com.jujeol.commons.dto.CommonResponseDto;
import com.jujeol.admin.ui.DrinkService;
import com.jujeol.drink.application.dto.DrinkResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DrinkController {

    private final DrinkService drinkService;

    @GetMapping("/drinks")
    public ResponseEntity<CommonResponseDto<List<AdminDrinkResponse>>> showDrinks(Pageable pageable) {
        Page<AdminDrinkResponse> drinkResponses = drinkService.findDrinks(pageable);
        final CommonResponseDto<List<AdminDrinkResponse>> response = PageResponseAssembler
                .assemble(drinkResponses);
        return ResponseEntity.ok(response);
    }

}
