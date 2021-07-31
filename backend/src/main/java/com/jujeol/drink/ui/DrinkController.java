package com.jujeol.drink.ui;

import com.jujeol.commons.dto.CommonResponse;
import com.jujeol.commons.dto.PageInfo;
import com.jujeol.commons.dto.PageResponseAssembler;
import com.jujeol.drink.application.DrinkService;
import com.jujeol.drink.application.ReviewService;
import com.jujeol.drink.application.dto.DrinkDto;
import com.jujeol.drink.application.dto.ReviewRequest;
import com.jujeol.drink.application.dto.ReviewResponse;
import com.jujeol.drink.domain.RecommendFactory;
import com.jujeol.drink.ui.dto.DrinkDetailResponse;
import com.jujeol.drink.ui.dto.DrinkSimpleResponse;
import com.jujeol.drink.ui.dto.SearchRequest;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DrinkController {

    private final DrinkService drinkService;
    private final ReviewService reviewService;
    private final RecommendFactory recommendFactory;

    @GetMapping("/drinks")
    public ResponseEntity<CommonResponse<List<DrinkSimpleResponse>>> showDrinksBySearch(
            @ModelAttribute SearchRequest searchRequest,
            @PageableDefault Pageable pageable
    ) {
        Page<DrinkDto> drinkDtos = drinkService
                .showDrinksBySearch(searchRequest.toDto(), pageable);
        return ResponseEntity
                .ok(PageResponseAssembler.assemble(drinkDtos.map(DrinkSimpleResponse::from)));
    }

    @GetMapping("/drinks/recommendation")
    public CommonResponse<List<DrinkSimpleResponse>> showDrinks(
            @AuthenticationPrincipal LoginMember loginMember,
            @PageableDefault(7) Pageable pageable
    ) {
        final Page<DrinkDto> drinkDtos;

        if(loginMember.isMember()) {
            drinkDtos = drinkService.showRecommendDrinks(recommendFactory.member(), pageable, loginMember);
            return PageResponseAssembler.assemble(drinkDtos.map(DrinkSimpleResponse::from));
        }

        drinkDtos = drinkService.showRecommendDrinks(recommendFactory.anonymous(), pageable, loginMember);
        return PageResponseAssembler.assemble(drinkDtos.map(DrinkSimpleResponse::from));
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

        PageInfo pageInfo = PageInfo.from(
                pageable.getPageNumber(),
                pageResponses.getTotalPages(),
                pageable.getPageSize(),
                pageResponses.getTotalElements());

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
