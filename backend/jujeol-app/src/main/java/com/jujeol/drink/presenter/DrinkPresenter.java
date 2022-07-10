package com.jujeol.drink.presenter;

import com.jujeol.drink.controller.response.DrinkDetailResponse;
import com.jujeol.drink.domain.model.Drink;
import com.jujeol.drink.service.DrinkService;
import com.jujeol.member.resolver.LoginMember;
import com.jujeol.model.DrinkWithPreference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DrinkPresenter {

    private final DrinkService drinkService;

    public DrinkDetailResponse showDrinkDetail(Long id, LoginMember loginMember) {

        if (loginMember.isMember()) {
            // TODO : 존재하지 않을 시 예외 처리 필요
            DrinkWithPreference drinkWithPreference = drinkService.findDrinkWithPreference(id, loginMember.getId()).orElseThrow();
            return DrinkDetailResponse.from(drinkWithPreference.getDrink(), drinkWithPreference.getPreference().getRate());
        }

        // TODO : 존재하지 않을 시 예외 처리 필요
        Drink drink = drinkService.findDrink(id).orElseThrow();
        return DrinkDetailResponse.from(drink, 0);
    }
}
