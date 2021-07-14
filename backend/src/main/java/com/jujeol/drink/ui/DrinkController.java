package com.jujeol.drink.ui;

import com.jujeol.commons.dto.CommonResponseDto;
import com.jujeol.drink.application.DrinkService;
import com.jujeol.drink.application.dto.DrinkDetailResponse;
import com.jujeol.drink.application.dto.DrinkSimpleResponse;
import com.jujeol.drink.application.dto.ReviewRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    @PostMapping("/drinks/{id}/reviews")
    public ResponseEntity<Void> createReview(@PathVariable Long id, @RequestBody ReviewRequest reviewRequest) {
        drinkService.createReview(id, reviewRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/drinks/{drinkId}/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long drinkId, @PathVariable Long reviewId) {
        drinkService.deleteReview(drinkId, reviewId);
        return ResponseEntity.ok().build();
    }
}
