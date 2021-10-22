package com.jujeol.drink.recommend.infrastructure.slope;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DataMatrix {

    private final Map<Long, Map<Long, ItemCounter>> matrix;
    private final Map<Long, Map<Long, Double>> dataByMember;

    public DataMatrix(List<DataModel> dataModel) {
        this.dataByMember = new HashMap<>();
        this.matrix = new HashMap<>();
        prepareMatrix(dataModel);
    }

    private void prepareMatrix(List<DataModel> dataModel) {
        for (DataModel model : dataModel) {
            final Long memberId = model.getMemberId();
            final Map<Long, Double> itemByMember = dataByMember
                    .computeIfAbsent(memberId, id -> new HashMap<>());

            for (Entry<Long, Double> itemPreference : itemByMember.entrySet()) {
                final Long itemId = itemPreference.getKey();
                final Double preference = itemPreference.getValue();
                if(itemId.equals(model.getItemId())) continue;

                final Map<Long, ItemCounter> primaryMap =
                        matrix.computeIfAbsent(model.getItemId(), id -> new HashMap<>());
                final Map<Long, ItemCounter> secondaryMap =
                        matrix.computeIfAbsent(itemId, id -> new HashMap<>());

                primaryMap.computeIfAbsent(itemId, id -> new ItemCounter()).addSum(model.getPreference() - preference);
                secondaryMap.computeIfAbsent(model.getItemId(), id -> new ItemCounter()).addSum(preference - model.getPreference());
            }
            itemByMember.put(model.getItemId(), model.getPreference());
        }
    }
    public Map<Long, Map<Long, ItemCounter>> getMatrix() {
        return matrix;
    }

    public Map<Long, Double> getDataByMember(Long id) {
        return dataByMember.getOrDefault(id, new HashMap<>());
    }
}
