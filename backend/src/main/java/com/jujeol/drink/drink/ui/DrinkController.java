package com.jujeol.drink.drink.ui;

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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DrinkController {

    private static final String EXPECTED_PREFERENCE = "expectedPreference";
    private static final String EXPECT_PREFERENCE = "expectPreference";
    private static final String PREFERENCE_AVG = "preferenceAvg";
    private static final String NO_SORT = "noSort";
    private final DrinkService drinkService;
    private final RecommendFactory recommendFactory;

    @GetMapping("/drinks")
    public ResponseEntity<CommonResponse<List<DrinkResponse>>> showDrinksInMainPage(
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = NO_SORT, required = false) String sortBy,
            @PageableDefault Pageable page,
            @AuthenticationPrincipal LoginMember loginMember
    ) {
        Page<DrinkDto> drinkDtos = new PageImpl<>(new ArrayList<>(), page, page.getPageSize());

        checkSortBy(sortBy);

        if (EXPECT_PREFERENCE.equals(sortBy) || EXPECTED_PREFERENCE.equals(sortBy)) {
            drinkDtos = drinkService
                    .showDrinksByExpect(category, recommendFactory.create(loginMember), page,
                            loginMember);
        }
        if (PREFERENCE_AVG.equals(sortBy)) {
            drinkDtos = drinkService
                    .showDrinksByPreference(category, page, loginMember);
        }
        if (NO_SORT.equals(sortBy)) {
            drinkDtos = drinkService.showAllDrinksByPage(page, loginMember, category);
        }

        if (loginMember.isAnonymous()) {
            return ResponseEntity.ok(PageResponseAssembler
                    .assemble(drinkDtos.map(drink -> DrinkResponse.from(drink, 0))));
        }

        return ResponseEntity
                .ok(PageResponseAssembler.assemble(drinkDtos.map(DrinkResponse::from)));
    }

    private void checkSortBy(String sortBy) {
        if (!EXPECT_PREFERENCE.equals(sortBy) && !PREFERENCE_AVG.equals(sortBy)
                && !EXPECTED_PREFERENCE.equals(sortBy) && !NO_SORT.equals(sortBy)) {
            throw new InvalidSortByException();
        }
    }
}
