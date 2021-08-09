package com.jujeol.drink.application;

import com.jujeol.drink.domain.RecommendForAnonymous;
import com.jujeol.drink.domain.RecommendForMember;
import com.jujeol.drink.domain.repository.DrinkRepository;
import com.jujeol.drink.infrastructure.recommend.RecommendationSystem;
import com.jujeol.member.ui.LoginMember;
import java.util.EnumMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class RecommendFactory {

    private enum MemberStatus {
        ANONYMOUS, MEMBER
    }
    private final Map<MemberStatus, RecommendStrategy> recommendStrategyMap;

    public RecommendFactory(RecommendationSystem recommendationSystem,
            DrinkRepository drinkRepository) {
        this.recommendStrategyMap = new EnumMap<>(MemberStatus.class);
        this.recommendStrategyMap.put(MemberStatus.ANONYMOUS, new RecommendForAnonymous(drinkRepository));
        this.recommendStrategyMap.put(MemberStatus.MEMBER, new RecommendForMember(recommendationSystem, drinkRepository));
    }

    public RecommendStrategy create(LoginMember loginMember) {
        if (loginMember.isMember()) {
            return recommendStrategyMap.get(MemberStatus.MEMBER);
        }
        return recommendStrategyMap.get(MemberStatus.ANONYMOUS);
    }
}
