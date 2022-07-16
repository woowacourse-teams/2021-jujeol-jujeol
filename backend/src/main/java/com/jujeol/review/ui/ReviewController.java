package com.jujeol.review.ui;

import com.jujeol.commons.aop.MemberOnly;
import com.jujeol.member.auth.ui.AuthenticationPrincipal;
import com.jujeol.member.auth.ui.LoginMember;
import com.jujeol.review.application.ReviewService;
import com.jujeol.review.ui.dto.ReviewUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PutMapping("/reviews/{reviewId}")
    @MemberOnly
    public ResponseEntity<Void> updateReview(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long reviewId,
            @RequestBody ReviewUpdateRequest reviewUpdateRequest
    ) {
        reviewService.updateReview(loginMember.getId(), reviewId, reviewUpdateRequest.getContent());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/reviews/{reviewId}")
    @MemberOnly
    public ResponseEntity<Void> deleteReview(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long reviewId
    ) {
        reviewService.deleteReview(loginMember.getId(), reviewId);
        return ResponseEntity.ok().build();
    }
}
