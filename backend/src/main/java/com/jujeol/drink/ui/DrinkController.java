package com.jujeol.drink.ui;

import com.jujeol.drink.application.DrinkService;
import com.jujeol.drink.application.dto.DrinkResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DrinkController {

    private DrinkService drinkService;

    public DrinkController(DrinkService drinkService) {
        this.drinkService = drinkService;
    }

    @GetMapping("/drinks")
    public ResponseEntity<List<DrinkResponse>> findDrinks() {
        return ResponseEntity.ok(drinkService.findDrinks());
    }
}
