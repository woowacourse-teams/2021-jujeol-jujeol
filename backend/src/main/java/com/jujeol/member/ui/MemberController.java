package com.jujeol.member.ui;

import com.jujeol.commons.dto.CommonResponse;
import com.jujeol.commons.dto.PageInfo;
import com.jujeol.commons.dto.PageResponseAssembler;
import com.jujeol.drink.application.dto.DrinkDto;
import com.jujeol.drink.application.dto.ReviewDto;
import com.jujeol.member.application.MemberService;
import com.jujeol.member.application.dto.MemberResponse;
import com.jujeol.member.application.dto.PreferenceRequest;
import com.jujeol.member.ui.dto.MemberDrinkResponse;
import com.jujeol.member.ui.dto.MemberReviewResponse;
import com.jujeol.member.ui.dto.ReviewDrinkResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members/me")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<CommonResponse<MemberResponse>> findMemberOfMine(
            @AuthenticationPrincipal LoginMember loginMember
    ) {
        return ResponseEntity
                .ok(CommonResponse.from(memberService.findMember(loginMember.getId())));
    }

    @PutMapping("/drinks/{id}/preference")
    public ResponseEntity<Void> createOrUpdatePreference(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable(name = "id") Long drinkId,
            @RequestBody PreferenceRequest preferenceRequest
    ) {
        memberService.createOrUpdatePreference(loginMember.getId(), drinkId, preferenceRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/drinks/{id}/preference")
    public ResponseEntity<Void> deletePreference(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable(name = "id") Long drinkId
    ) {
        memberService.deletePreference(loginMember.getId(), drinkId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/drinks")
    public ResponseEntity<CommonResponse<List<MemberDrinkResponse>>> showDrinksOfMine(
            @AuthenticationPrincipal LoginMember loginMember,
            @PageableDefault Pageable pageable
    ) {
        Page<DrinkDto> drinks = memberService.findDrinks(loginMember.getId(), pageable);
        Page<MemberDrinkResponse> responses = drinks.map(MemberDrinkResponse::from);

        return ResponseEntity.ok(PageResponseAssembler.assemble(responses));
    }

    @GetMapping("/reviews")
    public ResponseEntity<CommonResponse<List<MemberReviewResponse>>> showReviewsOfMine(
            @AuthenticationPrincipal LoginMember loginMember,
            @PageableDefault Pageable pageable
    ) {
        Page<ReviewDto> reviews = memberService.findReviews(loginMember.getId(), pageable);
        Page<MemberReviewResponse> responses = reviews
                .map(reviewDto -> MemberReviewResponse.create(
                        reviewDto.getId(),
                        reviewDto.getContent(),
                        reviewDto.getCreatedAt(),
                        reviewDto.getModifiedAt(),
                        ReviewDrinkResponse.create(
                                reviewDto.getDrinkDto().getId(),
                                reviewDto.getDrinkDto().getName(),
                                reviewDto.getDrinkDto().getImageUrl()
                        )
                ));
        return ResponseEntity.ok(PageResponseAssembler.assemble(responses));
    }
}
