package com.jujeol.review.ui;

import com.jujeol.commons.dto.CommonResponse;
import com.jujeol.commons.dto.PageInfo;
import com.jujeol.commons.dto.PageResponseAssembler;
import com.jujeol.drink.application.dto.ReviewCreateRequest;
import com.jujeol.member.auth.ui.AuthenticationPrincipal;
import com.jujeol.member.auth.ui.LoginMember;
import com.jujeol.review.application.ReviewService;
import com.jujeol.review.application.dto.ReviewRequest;
import com.jujeol.review.application.dto.ReviewWithAuthorDto;
import com.jujeol.review.ui.dto.MemberSimpleResponse;
import com.jujeol.review.ui.dto.ReviewResponse;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/reviews")
    public ResponseEntity<CommonResponse<List<ReviewResponse>>> showReviews(
            @PageableDefault(sort = "createdAt", direction = Direction.DESC) Pageable pageable,
            @RequestParam(required = false, name = "drink") Long drinkId
    ) {
        Page<ReviewWithAuthorDto> pageResponses = reviewService.showReviews(drinkId, pageable);
        List<ReviewResponse> reviewResponses = pageResponses.stream()
                .map(reviewWithAuthorDto -> ReviewResponse.create(
                        reviewWithAuthorDto,
                        MemberSimpleResponse.create(reviewWithAuthorDto.getMemberSimpleDto())
                ))
                .collect(Collectors.toList());

        PageInfo pageInfo = PageInfo.from(
                pageable.getPageNumber(),
                pageResponses.getTotalPages(),
                pageable.getPageSize(),
                pageResponses.getTotalElements());

        return ResponseEntity
                .ok(CommonResponse.from(reviewResponses, pageInfo));
    }

    @PostMapping("/reviews")
    public ResponseEntity<Void> createReview(
            @AuthenticationPrincipal LoginMember loginMember,
            @RequestBody ReviewCreateRequest reviewCreateRequest
    ) {
        reviewService.createReview(loginMember.getId(), reviewCreateRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> updateReview(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long reviewId,
            @RequestBody ReviewUpdateRequest reviewUpdateRequest
    ) {
        reviewService.updateReview(loginMember.getId(), reviewId, reviewUpdateRequest.getContent());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long reviewId
    ) {
        reviewService.deleteReview(loginMember.getId(), reviewId);
        return ResponseEntity.ok().build();
    }
}
