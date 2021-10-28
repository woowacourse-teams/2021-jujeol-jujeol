package com.jujeol.drink.recommend.infrastructure.slope;

import com.jujeol.preference.domain.PreferenceRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class DataMatrix {

    private final PreferenceRepository preferenceRepository;
    private List<DataModel> dataModel;
    private Map<Long, Map<Long, ItemCounter>> matrix;
    private Map<Long, Map<Long, Double>> dataByMember;

    public DataMatrix(PreferenceRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
        this.dataModel = new ArrayList<>();
        resetData();
    }

    public void prepareMatrix() {
        this.dataByMember = new ConcurrentHashMap<>();
        this.matrix = new ConcurrentHashMap<>();

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
        if (matrix == null) {
            this.dataModel.add(dataModel);
            return;
        }
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
        if (matrix == null) {
            this.dataModel.remove(new DataModel(memberId, itemId, 0.0));
            return;
        }
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
        if (matrix == null) {
            this.dataModel.remove(dataModel);
            this.dataModel.add(dataModel);
            return;
        }
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
        if(matrix == null) {
            prepareMatrix();
        }
        return matrix;
    }

    public Map<Long, Double> getDataByMember(Long id) {
        if(matrix == null) {
            prepareMatrix();
        }
        return dataByMember.getOrDefault(id, new HashMap<>());
    }

    public void resetData() {
        this.dataModel = preferenceRepository.findAll()
                .stream()
                .map(preference -> new DataModel(preference.getMember().getId(), preference.getDrink().getId(), preference.getRate()))
                .collect(Collectors.toList());
        this.matrix = null;
        this.dataByMember = null;
    }
}
