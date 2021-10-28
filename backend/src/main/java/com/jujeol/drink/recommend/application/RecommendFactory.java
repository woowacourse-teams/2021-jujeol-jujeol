package com.jujeol.drink.recommend.application;

import com.jujeol.drink.drink.domain.repository.DrinkRepository;
import com.jujeol.drink.recommend.domain.RecommendForAnonymous;
import com.jujeol.drink.recommend.domain.RecommendForMember;
import com.jujeol.drink.recommend.infrastructure.slope.DataMatrix;
import com.jujeol.drink.recommend.infrastructure.slope.Recommender;
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

    public RecommendFactory(Recommender recommender,
            DataMatrix dataMatrix,
            DrinkRepository drinkRepository,
            PreferenceRepository preferenceRepository
    ) {
        this.recommendStrategyMap = new EnumMap<>(MemberStatus.class);
        this.recommendStrategyMap
                .put(MemberStatus.ANONYMOUS, new RecommendForAnonymous(drinkRepository));
        this.recommendStrategyMap.put(MemberStatus.MEMBER,
                new RecommendForMember(drinkRepository, preferenceRepository, recommender, dataMatrix));
    }

    public RecommendStrategy create(LoginMember loginMember) {
        return loginMember.act()
                .ifMember(id -> recommendStrategyMap.get(MemberStatus.MEMBER))
                .ifAnonymous(() -> recommendStrategyMap.get(MemberStatus.ANONYMOUS))
                .getReturnValue(RecommendStrategy.class);
    }
}
