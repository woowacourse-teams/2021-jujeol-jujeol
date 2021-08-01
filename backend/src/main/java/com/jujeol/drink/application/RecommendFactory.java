package com.jujeol.drink.application;

import com.jujeol.drink.domain.RecommendForAnonymous;
import com.jujeol.drink.domain.RecommendForMember;
import com.jujeol.drink.domain.repository.DrinkRepository;
import com.jujeol.drink.infrastructure.recommend.RecommendationSystem;
import com.jujeol.member.ui.LoginMember;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
public class RecommendFactory {

    private final RecommendStrategy recommendStrategy;
    private final RecommendationSystem recommendationSystem;
    private final DrinkRepository drinkRepository;

    public RecommendFactory(RecommendationSystem recommendationSystem,
            DrinkRepository drinkRepository) {
        this.recommendStrategy = new RecommendForAnonymous(drinkRepository);
        this.recommendationSystem = recommendationSystem;
        this.drinkRepository = drinkRepository;
    }

    public RecommendStrategy create(LoginMember loginMember) {
        if(loginMember.isMember()) {
            return new RecommendForMember(recommendStrategy, recommendationSystem, drinkRepository);
        }
        return new RecommendForAnonymous(drinkRepository);
    }
}
