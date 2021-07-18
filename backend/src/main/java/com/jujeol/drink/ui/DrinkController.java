package com.jujeol.drink.ui;

import com.jujeol.commons.dto.CommonResponse;
import com.jujeol.commons.dto.PageInfo;
import com.jujeol.drink.application.DrinkService;
import com.jujeol.drink.application.ReviewService;
import com.jujeol.drink.application.dto.DrinkDto;
import com.jujeol.drink.application.dto.ReviewRequest;
import com.jujeol.drink.application.dto.ReviewResponse;
import com.jujeol.drink.ui.dto.DrinkDetailResponse;
import com.jujeol.drink.ui.dto.DrinkSimpleResponse;
import com.jujeol.member.ui.AuthenticationPrincipal;
import com.jujeol.member.ui.LoginMember;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
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
    private final ReviewService reviewService;

    @GetMapping("/drinks")
    public ResponseEntity<CommonResponse<List<DrinkSimpleResponse>>> showDrinks() {
        List<DrinkDto> drinkDtos = drinkService.showDrinks();
        List<DrinkSimpleResponse> drinkSimpleResponses = drinkDtos.stream()
                .map(DrinkSimpleResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity
                .ok(CommonResponse.from(drinkSimpleResponses, null));
    }

    @GetMapping("/drinks/{id}")
    public ResponseEntity<CommonResponse<DrinkDetailResponse>> showDrinkDetail(
            @PathVariable Long id,
            @AuthenticationPrincipal LoginMember loginMember
    ) {
        DrinkDto drinkDto = new DrinkDto();
        if (loginMember.isAnonymous()) {
            drinkDto = drinkService.showDrinkDetail(id);
        }
        if (loginMember.isMember()) {
            drinkDto = drinkService.showDrinkDetail(id, loginMember.getId());
        }
        DrinkDetailResponse drinkDetailResponse = DrinkDetailResponse.from(drinkDto);
        return ResponseEntity.ok(CommonResponse.from(drinkDetailResponse));
    }

    @GetMapping("/drinks/{id}/reviews")
    public ResponseEntity<CommonResponse<List<ReviewResponse>>> showReviews(
            @PathVariable Long id,
            @PageableDefault(sort = "createdAt", direction = Direction.DESC) Pageable pageable
    ) {
        Page<ReviewResponse> pageResponses = reviewService.showReviews(id, pageable);
        List<ReviewResponse> reviewResponses = pageResponses.stream().collect(Collectors.toList());

        // todo: 마지막 페이지를 초과하는 값을 받으면 마지막 페이지 반환
        PageInfo pageInfo = PageInfo.from(
                pageable.getPageNumber(),
                pageResponses.getTotalPages(),
                pageable.getPageSize());

        return ResponseEntity
                .ok(CommonResponse.from(reviewResponses, pageInfo));
    }

    @PostMapping("/drinks/{id}/reviews")
    public ResponseEntity<Void> createReview(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long id,
            @RequestBody ReviewRequest reviewRequest
    ) {
        reviewService.createReview(loginMember.getId(), id, reviewRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/drinks/{drinkId}/reviews/{reviewId}")
    public ResponseEntity<Void> updateReview(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long drinkId,
            @PathVariable Long reviewId,
            @RequestBody ReviewRequest reviewRequest
    ) {
        reviewService.updateReview(loginMember.getId(), drinkId, reviewId, reviewRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/drinks/{drinkId}/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long drinkId,
            @PathVariable Long reviewId) {
        reviewService.deleteReview(loginMember.getId(), drinkId, reviewId);
        return ResponseEntity.ok().build();
    }
}
