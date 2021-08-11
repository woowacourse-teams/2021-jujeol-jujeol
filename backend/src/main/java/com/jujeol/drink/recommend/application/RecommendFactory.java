package com.jujeol.drink.recommend.application;

import com.jujeol.drink.drink.domain.repository.DrinkRepository;
import com.jujeol.drink.recommend.domain.RecommendForAnonymous;
import com.jujeol.drink.recommend.domain.RecommendForMember;
import com.jujeol.drink.recommend.infrastructure.RecommendationSystem;
import com.jujeol.member.auth.ui.LoginMember;
import com.jujeol.preference.domain.PreferenceRepository;
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
            DrinkRepository drinkRepository,
            PreferenceRepository preferenceRepository
            ) {
        this.recommendStrategyMap = new EnumMap<>(MemberStatus.class);
        this.recommendStrategyMap.put(MemberStatus.ANONYMOUS, new RecommendForAnonymous(drinkRepository));
        this.recommendStrategyMap.put(MemberStatus.MEMBER, new RecommendForMember(recommendationSystem, drinkRepository, preferenceRepository));
    }

    public RecommendStrategy create(LoginMember loginMember) {
        if (loginMember.isMember()) {
            return recommendStrategyMap.get(MemberStatus.MEMBER);
        }
        return recommendStrategyMap.get(MemberStatus.ANONYMOUS);
    }
}
