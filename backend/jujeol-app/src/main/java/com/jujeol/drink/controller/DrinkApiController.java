package com.jujeol.drink.controller;

import com.jujeol.commons.CommonResponse;
import com.jujeol.commons.PageResponseAssembler;
import com.jujeol.drink.controller.response.DrinkDetailResponse;
import com.jujeol.drink.controller.response.MemberDrinkResponse;
import com.jujeol.drink.presenter.DrinkPresenter;
import com.jujeol.member.resolver.AuthenticationPrincipal;
import com.jujeol.member.resolver.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DrinkApiController {

    private final DrinkPresenter drinkPresenter;

    @GetMapping("/drinks/{id}")
    public ResponseEntity<CommonResponse<DrinkDetailResponse>> showDrinkDetail(
        @PathVariable Long id,
        @AuthenticationPrincipal LoginMember loginMember
    ) {
        return ResponseEntity.ok(CommonResponse.from(drinkPresenter.showDrinkDetail(id, loginMember)));
    }

    @GetMapping("/members/me/drinks")
    //TODO : Pageable 삭제 필요, Page 응답값 삭제 필요 (응용쪽에서 JPA 기술 의존적 삭제 필요)
    public ResponseEntity<CommonResponse<List<MemberDrinkResponse>>> showDrinksOfMine(
        @AuthenticationPrincipal LoginMember loginMember,
        @PageableDefault(size = 7) Pageable pageable
    ) {
        return ResponseEntity.ok(PageResponseAssembler.assemble(drinkPresenter.findDrinkOfMine(loginMember, pageable)));
    }
}
