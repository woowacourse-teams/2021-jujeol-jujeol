package com.jujeol.drink.recommend.infrastructure.slope;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.stereotype.Component;

@Component
public class Recommender {

    public List<RecommendationResponse> recommend(DataMatrix dataMatrix, Long memberId, double minPreference) {
        final Map<Long, Map<Long, ItemCounter>> matrix = dataMatrix.getMatrix();
        final Map<Long, Double> dataByMember = dataMatrix.getDataByMember(memberId);
        final List<RecommendationResponse> recommendItems = new ArrayList<>();

        for (Entry<Long, Map<Long, ItemCounter>> matrixEntry : matrix.entrySet()) {
            final Long primaryItemId = matrixEntry.getKey();
            final Map<Long, ItemCounter> matrixValue = matrixEntry.getValue();
            if(dataByMember.containsKey(primaryItemId)) continue;

            double sumValue = 0.0;
            long count = 0;

            for (Entry<Long, Double> itemPreference : dataByMember.entrySet()) {
                final Long itemId = itemPreference.getKey();
                final Double preference = itemPreference.getValue();
                final ItemCounter itemCounter = matrixValue.getOrDefault(itemId, new ItemCounter());
                final double deviation = itemCounter.getDeviation();
                sumValue += (preference + deviation) * itemCounter.getCount();
                count += itemCounter.getCount();
            }

            final double expectedPreference = sumValue / count;
            if(expectedPreference >= minPreference) {
                recommendItems.add(new RecommendationResponse(primaryItemId, expectedPreference));
            }
        }
        return recommendItems;
    }
}
