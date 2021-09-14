package com.jujeol.drink.drink.ui;

import com.jujeol.commons.aop.LogWithTime;
import com.jujeol.commons.dto.CommonResponse;
import com.jujeol.commons.dto.PageResponseAssembler;
import com.jujeol.drink.drink.application.DrinkService;
import com.jujeol.drink.drink.application.dto.DrinkDto;
import com.jujeol.drink.drink.exception.InvalidSortByException;
import com.jujeol.drink.drink.ui.dto.DrinkResponse;
import com.jujeol.drink.drink.ui.dto.SearchRequest;
import com.jujeol.drink.recommend.application.RecommendFactory;
import com.jujeol.member.auth.ui.AuthenticationPrincipal;
import com.jujeol.member.auth.ui.LoginMember;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DrinkController {

    static final String EXPECT_PREFERENCE = "expectPreference";
    static final String PREFERENCE_AVG = "preferenceAvg";

    private final DrinkService drinkService;
    private final RecommendFactory recommendFactory;

    @LogWithTime
    @GetMapping("/search")
    public ResponseEntity<CommonResponse<List<DrinkResponse>>> showDrinksBySearch(
            @ModelAttribute SearchRequest searchRequest,
            @PageableDefault Pageable pageable
    ) {
        Page<DrinkDto> drinkDtos = drinkService
                .showDrinksBySearch(searchRequest.toDto(), pageable);
        return ResponseEntity
                .ok(PageResponseAssembler.assemble(drinkDtos.map(DrinkResponse::from)));
    }

    @GetMapping("/drinks")
    public ResponseEntity<CommonResponse<List<DrinkResponse>>> showDrinksInMainPage(
            @RequestParam(required = false) String category,
            @RequestParam String sortBy,
            @PageableDefault Pageable page,
            @AuthenticationPrincipal LoginMember loginMember
    ) {
        Page<DrinkDto> drinkDtos = new PageImpl<>(new ArrayList<>(), page, page.getPageSize());

        checkSortBy(sortBy);
        
        if(EXPECT_PREFERENCE.equals(sortBy)) {
            drinkDtos = drinkService
                    .showDrinksByExpect(recommendFactory.create(loginMember), page, loginMember);
        }
        if(PREFERENCE_AVG.equals(sortBy)) {
            drinkDtos = drinkService
                    .showDrinksByPreference(category, page);
        }

        return ResponseEntity
                .ok(PageResponseAssembler.assemble(drinkDtos.map(DrinkResponse::from)));
    }

    private void checkSortBy(String sortBy) {
        if(!EXPECT_PREFERENCE.equals(sortBy) && !PREFERENCE_AVG.equals(sortBy)) {
            throw new InvalidSortByException();
        }
    }

    @GetMapping("/drinks/{id}")
    public ResponseEntity<CommonResponse<DrinkResponse>> showDrinkDetail(
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
        DrinkResponse drinkResponse = DrinkResponse.from(drinkDto);
        return ResponseEntity.ok(CommonResponse.from(drinkResponse));
    }
}
