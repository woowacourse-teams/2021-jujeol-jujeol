package com.jujeol.drink.infrastructure.recommend;

import static java.util.stream.Collectors.toList;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecommendationSystem {

    private final RecommendationDataModel recommendationDataModel;

    public List<Long> recommend(Long memberId, int howMany) {
        final UserBasedRecommender recommender = getRecommender();
        final List<RecommendedItem> recommend;
        try {
            recommend = recommender.recommend(memberId, howMany);
        } catch (TasteException e) {
            throw new IllegalStateException();
        }

        final List<Long> itemList =
                recommend.stream().mapToLong(RecommendedItem::getItemID).boxed().collect(toList());
        return itemList;
    }

    private UserNeighborhood getUserNeighborhood(double threshold, UserSimilarity similarity, DataModel dataModel) {
        return new ThresholdUserNeighborhood(threshold, similarity, dataModel);
    }

    private UserBasedRecommender getRecommender() {
        final DataModel dataModel = recommendationDataModel.dataModel();
        final UserSimilarity similarity = getSimilarity(dataModel);
        return new GenericUserBasedRecommender(dataModel, getUserNeighborhood(0.1, similarity, dataModel),
                similarity);
    }

    private UserSimilarity getSimilarity(DataModel dataModel) {
        try {
            return new PearsonCorrelationSimilarity(dataModel);
        } catch (TasteException e) {
            throw new IllegalStateException();
        }
    }
}
