package com.jujeol.drink.drink.ui;

import com.jujeol.commons.aop.LogWithTime;
import com.jujeol.commons.dto.CommonResponse;
import com.jujeol.commons.dto.PageResponseAssembler;
import com.jujeol.drink.drink.application.DrinkService;
import com.jujeol.drink.drink.application.dto.DrinkDto;
import com.jujeol.drink.drink.ui.dto.DrinkDetailResponse;
import com.jujeol.drink.drink.ui.dto.DrinkSimpleResponse;
import com.jujeol.drink.drink.ui.dto.SearchRequest;
import com.jujeol.drink.recommend.application.RecommendFactory;
import com.jujeol.member.auth.ui.AuthenticationPrincipal;
import com.jujeol.member.auth.ui.LoginMember;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DrinkController {

    private final DrinkService drinkService;
    private final RecommendFactory recommendFactory;

    @LogWithTime
    @GetMapping("/drinks")
    public ResponseEntity<CommonResponse<List<DrinkSimpleResponse>>> showDrinksBySearch(
            @ModelAttribute SearchRequest searchRequest,
            @PageableDefault Pageable pageable
    ) {
        Page<DrinkDto> drinkDtos = drinkService
                .showDrinksBySearch(searchRequest.toDto(), pageable);
        return ResponseEntity
                .ok(PageResponseAssembler.assemble(drinkDtos.map(DrinkSimpleResponse::from)));
    }

    @LogWithTime
    @GetMapping("/drinks/recommendation")
    public CommonResponse<List<DrinkSimpleResponse>> showDrinks(
            @AuthenticationPrincipal LoginMember loginMember,
            @PageableDefault(7) Pageable pageable
    ) {
        final Page<DrinkDto> drinkDtos;
        drinkDtos = drinkService
                .showRecommendDrinks(recommendFactory.create(loginMember), pageable, loginMember);
        return PageResponseAssembler.assemble(drinkDtos.map(DrinkSimpleResponse::from));
    }

    @GetMapping("/drinks/{id}")
    public ResponseEntity<CommonResponse<DrinkDetailResponse>> showDrinkDetail(
            @PathVariable Long id,
            @AuthenticationPrincipal LoginMember loginMember
    ) {
        DrinkDto drinkDto = new DrinkDto();
        if (loginMember.isAnonymous()) {
            drinkDto = drinkService.showDrinkDetail(id);
        }
        if (loginMember.isMember()) {
            drinkDto = drinkService.showDrinkDetail(id, loginMember.getId());
        }
        DrinkDetailResponse drinkDetailResponse = DrinkDetailResponse.from(drinkDto);
        return ResponseEntity.ok(CommonResponse.from(drinkDetailResponse));
    }
}
