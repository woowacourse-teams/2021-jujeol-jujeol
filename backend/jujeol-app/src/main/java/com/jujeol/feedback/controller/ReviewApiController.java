package com.jujeol.feedback.controller;

import com.jujeol.commons.CommonResponse;
import com.jujeol.commons.PageResponseAssembler;
import com.jujeol.feedback.controller.request.ReviewCreateRequest;
import com.jujeol.feedback.controller.request.ReviewUpdateRequest;
import com.jujeol.feedback.controller.response.MemberReviewResponse;
import com.jujeol.feedback.controller.response.ReviewResponse;
import com.jujeol.feedback.presenter.ReviewPresenter;
import com.jujeol.member.resolver.AuthenticationPrincipal;
import com.jujeol.member.resolver.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewApiController {

    private final ReviewPresenter reviewPresenter;

    //TODO : Pageable 삭제 필요, Page 응답값 삭제 필요 (응용쪽에서 JPA 기술 의존적 삭제 필요)
    @GetMapping("/members/me/reviews")
    public ResponseEntity<CommonResponse<List<MemberReviewResponse>>> showReviewsOfMine(
        @AuthenticationPrincipal LoginMember loginMember,
        @PageableDefault(size = 3) Pageable pageable
    ) {
        return ResponseEntity.ok(PageResponseAssembler.assemble(reviewPresenter.getMyReviews(loginMember, pageable)));
    }

    //TODO : Pageable 삭제 필요, Page 응답값 삭제 필요 (응용쪽에서 JPA 기술 의존적 삭제 필요)
    @GetMapping("/reviews")
    public ResponseEntity<CommonResponse<List<ReviewResponse>>> showReviews(
        @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
        @RequestParam(required = false, name = "drink") Long drinkId
    ) {
        return ResponseEntity.ok(PageResponseAssembler.assemble(reviewPresenter.reviewList(drinkId, pageable)));
    }

    @PostMapping("/reviews")
    public ResponseEntity<Void> createReview(
        @AuthenticationPrincipal LoginMember loginMember,
        @RequestBody ReviewCreateRequest reviewCreateRequest
    ) {
        reviewPresenter.createReview(loginMember, reviewCreateRequest, LocalDateTime.now());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> updateReview(
        @AuthenticationPrincipal LoginMember loginMember,
        @PathVariable Long reviewId,
        @RequestBody ReviewUpdateRequest reviewUpdateRequest
    ) {
        reviewPresenter.updateReview(loginMember, reviewId, reviewUpdateRequest.getContent());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(
        @AuthenticationPrincipal LoginMember loginMember,
        @PathVariable Long reviewId
    ) {
        reviewPresenter.deleteReview(loginMember, reviewId);
        return ResponseEntity.ok().build();
    }
}
