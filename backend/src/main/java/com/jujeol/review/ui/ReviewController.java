package com.jujeol.review.ui;

import com.jujeol.commons.dto.CommonResponse;
import com.jujeol.commons.dto.PageResponseAssembler;
import com.jujeol.member.auth.ui.AuthenticationPrincipal;
import com.jujeol.member.auth.ui.LoginMember;
import com.jujeol.review.application.ReviewService;
import com.jujeol.review.application.dto.ReviewRequest;
import com.jujeol.review.application.dto.ReviewWithAuthorDto;
import com.jujeol.review.ui.dto.MemberSimpleResponse;
import com.jujeol.review.ui.dto.ReviewResponse;
import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/drinks/{id}/reviews")
    public ResponseEntity<CommonResponse<List<ReviewResponse>>> showReviews(
            @PathVariable Long id,
            @PageableDefault(sort = "createdAt", direction = Direction.DESC) Pageable pageable
    ) {
        Page<ReviewWithAuthorDto> reviewWithAuthorDtos = reviewService.showReviews(id, pageable);
        return ResponseEntity.ok(
                PageResponseAssembler.assemble(
                        reviewWithAuthorDtos.map(reviewWithAuthorDto -> ReviewResponse.create(
                                reviewWithAuthorDto,
                                MemberSimpleResponse
                                        .create(reviewWithAuthorDto.getMemberSimpleDto())
                        ))
                )
        );
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
            @PathVariable Long reviewId
    ) {
        reviewService.deleteReview(loginMember.getId(), drinkId, reviewId);
        return ResponseEntity.ok().build();
    }
}
