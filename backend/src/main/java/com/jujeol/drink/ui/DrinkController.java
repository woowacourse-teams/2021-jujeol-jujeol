package com.jujeol.drink.ui;

import com.jujeol.commons.dto.CommonResponseDto;
import com.jujeol.commons.dto.PageInfo;
import com.jujeol.drink.application.DrinkService;
import com.jujeol.drink.application.dto.DrinkDetailResponse;
import com.jujeol.drink.application.dto.DrinkSimpleResponse;
import com.jujeol.drink.application.dto.ReviewRequest;
import com.jujeol.drink.application.dto.ReviewResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class DrinkController {

    private final DrinkService drinkService;

    @GetMapping("/drinks")
    public ResponseEntity<CommonResponseDto<List<DrinkSimpleResponse>>> showDrinks() {
        // todo 페이지 네이션
        List<DrinkSimpleResponse> drinkSimpleResponse = drinkService.showDrinks();
        return ResponseEntity.ok(CommonResponseDto.fromList(drinkSimpleResponse, drinkSimpleResponse.size(), null));
    }

    @GetMapping("/drinks/{id}")
    public ResponseEntity<CommonResponseDto<DrinkDetailResponse>> showDrinkDetail(@PathVariable Long id) {
        // TODO: member & preference 추가
        DrinkDetailResponse drinkResponse = drinkService.showDrinkDetail(id);
        return ResponseEntity.ok(CommonResponseDto.fromOne(drinkResponse));
    }

    @GetMapping("/drinks/{id}/reviews")
    public ResponseEntity<CommonResponseDto<List<ReviewResponse>>> showReviews(
            @PathVariable Long id,
            @PageableDefault Pageable pageable
    ) {
        Page<ReviewResponse> pageResponses = drinkService.showReviews(id, pageable);
        List<ReviewResponse> reviewResponses = pageResponses.stream().collect(Collectors.toList());

        // todo: 마지막 페이지를 초과하는 값을 받으면 마지막 페이지 반환
        PageInfo pageInfo = PageInfo.from(
                pageable.getPageNumber(),
                pageResponses.getTotalPages(),
                pageable.getPageSize());

        return ResponseEntity.ok(CommonResponseDto.fromList(reviewResponses, reviewResponses.size(), pageInfo));
    }

    @PostMapping("/drinks/{id}/reviews")
    public ResponseEntity<Void> createReview(@PathVariable Long id, @RequestBody ReviewRequest reviewRequest) {
        drinkService.createReview(id, reviewRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/drinks/{drinksId}/reviews/{reviewId}")
    public ResponseEntity<Void> updateReview(
        @PathVariable Long drinksId,
        @PathVariable Long reviewId,
        @RequestBody ReviewRequest reviewRequest
    ) {
        drinkService.updateReview(drinksId, reviewId, reviewRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/drinks/{drinkId}/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long drinkId, @PathVariable Long reviewId) {
        drinkService.deleteReview(drinkId, reviewId);
        return ResponseEntity.ok().build();
    }
}
