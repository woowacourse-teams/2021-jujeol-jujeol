package com.jujeol.feedback.controller;

import com.jujeol.commons.CommonResponse;
import com.jujeol.commons.PageResponseAssembler;
import com.jujeol.feedback.controller.response.MemberReviewResponse;
import com.jujeol.feedback.presenter.FeedbackPresenter;
import com.jujeol.member.resolver.AuthenticationPrincipal;
import com.jujeol.member.resolver.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FeedbackApiController {

    private final FeedbackPresenter feedbackPresenter;

    //TODO : Pageable 삭제 필요, Page 응답값 삭제 필요 (응용쪽에서 JPA 기술 의존적 삭제 필요)
    @GetMapping("/members/me/reviews")
    public ResponseEntity<CommonResponse<List<MemberReviewResponse>>> showReviewsOfMine(
        @AuthenticationPrincipal LoginMember loginMember,
        @PageableDefault(size = 3) Pageable pageable
    ) {
        return ResponseEntity.ok(PageResponseAssembler.assemble(feedbackPresenter.getMyReviews(loginMember, pageable)));
    }
}
