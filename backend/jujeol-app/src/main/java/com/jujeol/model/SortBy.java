package com.jujeol.model;

public enum SortBy {
    NO_SORT("noSort"),
    PREFERENCE_AVG("preferenceAvg"),
    EXPECT_PREFERENCE("expectPreference"),
    EXPECTED_PREFERENCE("expectedPreference");

    private final String value;

    SortBy(String value) {
        this.value = value;
    }

    public boolean hasSameValue(String value) {
        return this.value.equals(value);
    }
}
