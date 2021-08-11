package com.jujeol.drink.recommend.infrastructure;

import static java.util.stream.Collectors.toList;

import com.jujeol.preference.domain.Preference;
import com.jujeol.preference.domain.PreferenceRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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

    private final PreferenceRepository preferenceRepository;

    public List<Long> recommend(Long memberId, int howMany) {
        final List<Preference> preferences = preferenceRepository.findAll();
        return recommend(memberId, howMany, preferences);
    }

    public List<Long> recommend(Long memberId, int howMany, List<Preference> preferences) {
        final UserBasedRecommender recommender = getRecommender(preferences);
        try {
            return recommender.recommend(memberId, howMany, true)
                    .stream()
                    .mapToLong(RecommendedItem::getItemID)
                    .boxed()
                    .collect(toList());
        } catch (TasteException e) {
            return new ArrayList<>();
        }
    }

    private UserNeighborhood getUserNeighborhood(double threshold, UserSimilarity similarity,
            DataModel dataModel) {
        return new ThresholdUserNeighborhood(threshold, similarity, dataModel);
    }

    private UserBasedRecommender getRecommender(List<Preference> preferences) {
        final List<org.apache.mahout.cf.taste.model.Preference> data = preferences.stream().map(
                MemberPreference::new)
                .collect(Collectors.toList());

        final DataModel dataModel = new PreferenceDataModel(data);
        final UserSimilarity similarity = getSimilarity(dataModel);
        return new GenericUserBasedRecommender(dataModel,
                getUserNeighborhood(0.1, similarity, dataModel),
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
