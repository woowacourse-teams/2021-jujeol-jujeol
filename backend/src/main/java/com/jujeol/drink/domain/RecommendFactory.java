package com.jujeol.drink.domain;

import com.jujeol.drink.domain.repository.DrinkRepository;
import com.jujeol.drink.infrastructure.recommend.RecommendationSystem;
import org.springframework.stereotype.Component;

@Component
public class RecommendFactory {

    private final RecommendState recommendState;
    private final RecommendationSystem recommendationSystem;
    private final DrinkRepository drinkRepository;

    public RecommendFactory(RecommendationSystem recommendationSystem,
            DrinkRepository drinkRepository) {
        this.recommendState = new RecommendAnonymous(drinkRepository);
        this.recommendationSystem = recommendationSystem;
        this.drinkRepository = drinkRepository;
    }

    public RecommendState member() {
        return new RecommendMember(recommendState, recommendationSystem, drinkRepository);
    }

    public RecommendState anonymous() {
        return new RecommendAnonymous(drinkRepository);
    }
}
