package com.jujeol.drink.controller;

import com.jujeol.commons.CommonResponse;
import com.jujeol.drink.controller.response.DrinkDetailResponse;
import com.jujeol.drink.presenter.DrinkPresenter;
import com.jujeol.member.resolver.AuthenticationPrincipal;
import com.jujeol.member.resolver.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
}
