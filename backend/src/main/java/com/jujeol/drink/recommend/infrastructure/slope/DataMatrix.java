package com.jujeol.drink.recommend.infrastructure.slope;

import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataMatrix {

    private final Map<Long, Map<Long, ItemCounter>> matrix;
    private final Map<Long, List<DataModel>> dataByMember;

    public DataMatrix(List<DataModel> dataModel) {
        this.dataByMember = groupingByMember(dataModel);
        this.matrix = new HashMap<>();

        for (List<DataModel> dataByMember : dataByMember.values()) {
            for (DataModel model : dataByMember) {
                final Long itemKey = model.getItemId();
                matrix.computeIfAbsent(itemKey, key -> new HashMap<>());
                for (DataModel model2 : dataByMember) {
                    if (itemKey.equals(model2.getItemId())) {
                        continue;
                    }
                    final Map<Long, ItemCounter> eachItem = matrix.get(itemKey);
                    final ItemCounter itemCounter =
                            eachItem.computeIfAbsent(model2.getItemId(), key -> new ItemCounter());
                    itemCounter.addSum(model.getPreference() - model2.getPreference());
                }
            }
        }
    }

    private Map<Long, List<DataModel>> groupingByMember(List<DataModel> dataModel) {
        return dataModel.stream()
                .collect(groupingBy(DataModel::getMemberId));
    }
    public Map<Long, Map<Long, ItemCounter>> getMatrix() {
        return matrix;
    }

    public List<DataModel> getDataByMember(Long memberId) {
        return dataByMember.getOrDefault(memberId, new ArrayList<>());
    }
}
