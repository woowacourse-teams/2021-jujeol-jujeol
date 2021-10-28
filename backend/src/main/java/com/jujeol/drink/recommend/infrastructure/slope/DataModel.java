package com.jujeol.drink.recommend.infrastructure.slope;

import java.util.Objects;

public class DataModel {

    private Long memberId;
    private Long itemId;
    private double preference;

    public DataModel(Long memberId, Long itemId, double preference) {
        this.memberId = memberId;
        this.itemId = itemId;
        this.preference = preference;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getItemId() {
        return itemId;
    }

    public double getPreference() {
        return preference;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DataModel dataModel = (DataModel) o;
        return Objects.equals(getMemberId(), dataModel.getMemberId()) && Objects
                .equals(getItemId(), dataModel.getItemId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMemberId(), getItemId());
    }
}
