package com.jujeol.drink.controller;

import com.jujeol.commons.CommonResponse;
import com.jujeol.commons.PageResponseAssembler;
import com.jujeol.drink.controller.requeset.AdminDrinkSaveRequest;
import com.jujeol.drink.controller.response.AdminDrinkResponse;
import com.jujeol.drink.presenter.DrinkAdminPresenter;
import com.jujeol.member.resolver.AuthenticationPrincipal;
import com.jujeol.member.resolver.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class DrinkAdminController {

    private final DrinkAdminPresenter drinkAdminPresenter;

    @PostMapping("/drinks")
    public CommonResponse<?> saveDrinks(
        @ModelAttribute AdminDrinkSaveRequest adminDrinkSaveRequest,
        @RequestPart MultipartFile image
    ) {
        drinkAdminPresenter.saveDrink(adminDrinkSaveRequest, image);
        return CommonResponse.ok();
    }

    @GetMapping("/drinks")
    //TODO : Pageable 삭제 필요, Page 응답값 삭제 필요 (응용쪽에서 JPA 기술 의존적 삭제 필요)
    public CommonResponse<List<AdminDrinkResponse>> showDrinks(
        @PageableDefault(value = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
        @AuthenticationPrincipal LoginMember loginMember) {
        return PageResponseAssembler.assemble(drinkAdminPresenter.showAdminDrinks(pageable, loginMember));
    }
}
