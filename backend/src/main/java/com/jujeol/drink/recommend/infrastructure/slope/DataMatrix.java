package com.jujeol.drink.recommend.infrastructure.slope;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class DataMatrix {

    private final Map<Long, Map<Long, ItemCounter>> matrix;
    private final Map<Long, Map<Long, Double>> dataByMember;

    public DataMatrix(List<DataModel> dataModel) {
        this.dataByMember = new ConcurrentHashMap<>();
        this.matrix = new ConcurrentHashMap<>();
        prepareMatrix(dataModel);
    }

    private void prepareMatrix(List<DataModel> dataModel) {
        for (DataModel model : dataModel) {
            final Long memberId = model.getMemberId();
            final Map<Long, Double> itemByMember = dataByMember
                    .computeIfAbsent(memberId, id -> new ConcurrentHashMap<>());

            for (Entry<Long, Double> itemPreference : itemByMember.entrySet()) {
                final Long itemId = itemPreference.getKey();
                final Double preference = itemPreference.getValue();
                if (itemId.equals(model.getItemId())) {
                    continue;
                }

                final Map<Long, ItemCounter> primaryMap =
                        matrix.computeIfAbsent(model.getItemId(), id -> new ConcurrentHashMap<>());
                final Map<Long, ItemCounter> secondaryMap =
                        matrix.computeIfAbsent(itemId, id -> new ConcurrentHashMap<>());

                primaryMap.computeIfAbsent(itemId, id -> new ItemCounter())
                        .addSum(model.getPreference() - preference);
                secondaryMap.computeIfAbsent(model.getItemId(), id -> new ItemCounter())
                        .addSum(preference - model.getPreference());
            }
            itemByMember.put(model.getItemId(), model.getPreference());
        }
    }

    public void addData(DataModel dataModel) {
        final Map<Long, Double> itemWithPreference = dataByMember
                .computeIfAbsent(dataModel.getMemberId(), id -> new ConcurrentHashMap<>());
        itemWithPreference.put(dataModel.getItemId(), dataModel.getPreference());

        final Map<Long, ItemCounter> primaryItemCounter = matrix
                .computeIfAbsent(dataModel.getItemId(), id -> new ConcurrentHashMap<>());

        for (Entry<Long, Map<Long, ItemCounter>> matrixEntry : matrix.entrySet()) {
            final Long targetItemKey = matrixEntry.getKey();
            final Map<Long, ItemCounter> targetItemCounter = matrixEntry.getValue();

            if (targetItemKey.equals(dataModel.getItemId()) || !itemWithPreference.containsKey(targetItemKey)) {
                continue;
            }

            final ItemCounter primaryCounter = primaryItemCounter
                    .computeIfAbsent(targetItemKey, id -> new ItemCounter());
            final ItemCounter targetCounter = targetItemCounter
                    .computeIfAbsent(dataModel.getItemId(), id -> new ItemCounter());

            final Double keyPreference = itemWithPreference.get(targetItemKey);

            targetCounter.addSum(keyPreference - dataModel.getPreference());
            primaryCounter.addSum(dataModel.getPreference() - keyPreference);
        }
    }

    public void removeData(Long memberId, Long itemId) {
        final Map<Long, Double> itemByMember = dataByMember
                .computeIfAbsent(memberId, id -> new ConcurrentHashMap<>());

        if ( !itemByMember.containsKey(itemId)) {
            throw new IllegalStateException("no preference exists");
        }

        final Double originalPreference = itemByMember.get(itemId);
        final Map<Long, ItemCounter> primaryItemCount = matrix.get(itemId);

        for (Entry<Long, Map<Long, ItemCounter>> matrixEntry : matrix.entrySet()) {
            final Long itemIdKey = matrixEntry.getKey();
            final Map<Long, ItemCounter> targetItemCount = matrixEntry.getValue();

            if(itemIdKey.equals(itemId) || !targetItemCount.containsKey(itemId) || !itemByMember.containsKey(itemIdKey)) {
                continue;
            }

            final Double targetPreference = itemByMember.get(itemIdKey);
            targetItemCount.get(itemId).minusSum(targetPreference - originalPreference);
            primaryItemCount.get(itemIdKey).minusSum(originalPreference - targetPreference);
        }

        itemByMember.remove(itemId);
    }

    public void updateData(DataModel dataModel) {
        final Map<Long, Double> itemByMember = dataByMember
                .computeIfAbsent(dataModel.getMemberId(), id -> new ConcurrentHashMap<>());

        if ( !itemByMember.containsKey(dataModel.getItemId())) {
            throw new IllegalStateException("no preference exists");
        }

        final Double originalPreference = itemByMember.get(dataModel.getItemId());
        final Map<Long, ItemCounter> primaryItemCount = matrix.get(dataModel.getItemId());

        for (Entry<Long, Map<Long, ItemCounter>> matrixEntry : matrix.entrySet()) {
            final Long itemIdKey = matrixEntry.getKey();
            final Map<Long, ItemCounter> targetItemCount = matrixEntry.getValue();

            if(itemIdKey.equals(dataModel.getItemId()) || !targetItemCount.containsKey(dataModel.getItemId()) || !itemByMember.containsKey(itemIdKey)) {
                continue;
            }

            targetItemCount.get(dataModel.getItemId()).updateSum(originalPreference - dataModel.getPreference());
            primaryItemCount.get(itemIdKey).updateSum(dataModel.getPreference() - originalPreference);
        }

        itemByMember.put(dataModel.getItemId(), dataModel.getPreference());
    }

    public Map<Long, Map<Long, ItemCounter>> getMatrix() {
        return matrix;
    }

    public Map<Long, Double> getDataByMember(Long id) {
        return dataByMember.getOrDefault(id, new HashMap<>());
    }
}
