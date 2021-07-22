package com.jujeol.member.ui;

import com.jujeol.commons.dto.CommonResponse;
import com.jujeol.commons.dto.PageInfo;
import com.jujeol.drink.application.dto.DrinkDto;
import com.jujeol.drink.application.dto.ReviewResponse;
import com.jujeol.member.application.MemberService;
import com.jujeol.member.application.dto.MemberResponse;
import com.jujeol.member.application.dto.PreferenceRequest;
import com.jujeol.member.ui.dto.MemberDrinkResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
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
        List<MemberDrinkResponse> responses = drinks
                .map(MemberDrinkResponse::from)
                .toList();

        PageInfo pageInfo = PageInfo.from(
                pageable.getPageNumber(), drinks.getTotalPages(), pageable.getPageSize());
        return ResponseEntity.ok(CommonResponse.from(responses, pageInfo));
    }
}
