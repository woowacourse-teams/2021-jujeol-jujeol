package com.jujeol.drink.presenter;

import com.jujeol.drink.controller.requeset.DrinkSearchRequest;
import com.jujeol.drink.controller.response.DrinkDetailResponse;
import com.jujeol.drink.controller.response.DrinkSearchResponse;
import com.jujeol.drink.controller.response.MemberDrinkResponse;
import com.jujeol.drink.domain.model.Drink;
import com.jujeol.drink.service.DrinkService;
import com.jujeol.member.resolver.LoginMember;
import com.jujeol.model.PreferenceWithDrink;
import com.jujeol.model.SearchWords;
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
        if (loginMember.isAnonymous()) {
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

    public Page<DrinkSearchResponse> showDrinksBySearch(DrinkSearchRequest searchRequest, LoginMember loginMember, Pageable pageable) {
        if (loginMember.isMember()) {
            return drinkService.searchForMember(loginMember.getId(), SearchWords.create(searchRequest.getKeyword()), pageable)
                .map(preferenceWithDrink -> DrinkSearchResponse.builder()
                    .id(preferenceWithDrink.getDrink().getDrinkId())
                    .description(preferenceWithDrink.getDrink().getDescription().value())
                    .alcoholByVolume(preferenceWithDrink.getDrink().getAlcoholByVolume().value())
                    .englishName(preferenceWithDrink.getDrink().getEnglishName().value())
                    .name(preferenceWithDrink.getDrink().getName().value())
                    .category(
                        DrinkSearchResponse.CategoryResponse.builder()
                            .id(preferenceWithDrink.getDrink().getCategory().getId())
                            .key(preferenceWithDrink.getDrink().getCategory().getKey())
                            .name(preferenceWithDrink.getDrink().getName().value())
                            .build()
                    )
                    .imageResponse(
                        DrinkSearchResponse.ImageResponse.builder()
                            .small(preferenceWithDrink.getDrink().getImageFilePath().getSmallImageFilePath())
                            .medium(preferenceWithDrink.getDrink().getImageFilePath().getMediumImageFilePath())
                            .large(preferenceWithDrink.getDrink().getImageFilePath().getLargeImageFilePath())
                            .build()
                    )
                    .expectedPreference(preferenceWithDrink.getExpectedPreference())
                    .preferenceAvg(preferenceWithDrink.getDrink().getPreferenceAvg())
                    .preferenceRate(preferenceWithDrink.getPreference().getRate())
                    .build());
        } else {
            return drinkService.searchForAnonymous(SearchWords.create(searchRequest.getKeyword()), pageable)
                .map(drink -> DrinkSearchResponse.builder()
                    .id(drink.getDrinkId())
                    .description(drink.getDescription().value())
                    .alcoholByVolume(drink.getAlcoholByVolume().value())
                    .englishName(drink.getEnglishName().value())
                    .name(drink.getName().value())
                    .category(
                        DrinkSearchResponse.CategoryResponse.builder()
                            .id(drink.getCategory().getId())
                            .key(drink.getCategory().getKey())
                            .name(drink.getName().value())
                            .build()
                    )
                    .imageResponse(
                        DrinkSearchResponse.ImageResponse.builder()
                            .small(drink.getImageFilePath().getSmallImageFilePath())
                            .medium(drink.getImageFilePath().getMediumImageFilePath())
                            .large(drink.getImageFilePath().getLargeImageFilePath())
                            .build()
                    )
                    .expectedPreference(0)
                    .preferenceAvg(drink.getPreferenceAvg())
                    .preferenceRate(0)
                    .build());
        }
    }
}
