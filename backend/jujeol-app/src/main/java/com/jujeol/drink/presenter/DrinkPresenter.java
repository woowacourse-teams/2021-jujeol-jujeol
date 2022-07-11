package com.jujeol.drink.presenter;

import com.jujeol.drink.controller.response.DrinkDetailResponse;
import com.jujeol.drink.controller.response.MemberDrinkResponse;
import com.jujeol.drink.domain.model.Drink;
import com.jujeol.drink.service.DrinkService;
import com.jujeol.member.resolver.LoginMember;
import com.jujeol.model.PreferenceWithDrink;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DrinkPresenter {

    private final DrinkService drinkService;

    public DrinkDetailResponse showDrinkDetail(Long id, LoginMember loginMember) {

        if (loginMember.isMember()) {
            // TODO : 존재하지 않을 시 예외 처리 필요
            PreferenceWithDrink preferenceWithDrink = drinkService.findDrinkWithPreference(id, loginMember.getId()).orElseThrow();
            return DrinkDetailResponse.from(preferenceWithDrink.getDrink(), preferenceWithDrink.getPreference().getRate());
        }

        // TODO : 존재하지 않을 시 예외 처리 필요
        Drink drink = drinkService.findDrink(id).orElseThrow();
        return DrinkDetailResponse.from(drink, 0);
    }

    //TODO : Pageable 삭제 필요, Page 응답값 삭제 필요 (응용쪽에서 JPA 기술 의존적 삭제 필요)
    public Page<MemberDrinkResponse> findDrinkOfMine(LoginMember loginMember, Pageable pageable) {
        // TODO : unauthorized 정의 필요
        if(loginMember.isAnonymous()) {
            throw new IllegalArgumentException();
        }
        return drinkService.findDrinksWithPreferencePage(loginMember.getId(), pageable)
            .map(preferenceWithDrink ->
                new MemberDrinkResponse(
                    preferenceWithDrink.getDrink().getDrinkId(),
                    preferenceWithDrink.getDrink().getName().value(),
                    preferenceWithDrink.getDrink().getImageFilePath().getMediumImageFilePath(),
                    preferenceWithDrink.getPreference().getRate()
                    )
            );
    }
}
