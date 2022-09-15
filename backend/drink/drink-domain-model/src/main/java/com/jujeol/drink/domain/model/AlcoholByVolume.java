package com.jujeol.drink.domain.model;

import com.jujeol.drink.domain.exception.InvalidAlcoholByVolumeException;

import java.util.Objects;

public class AlcoholByVolume {

    private Double value;

    private AlcoholByVolume(Double alcoholByVolume) {
        if (Objects.isNull(alcoholByVolume)) {
            return;
        }

        if (alcoholByVolume < 0 || alcoholByVolume > 100) {
            throw new InvalidAlcoholByVolumeException();
        }
        this.value = alcoholByVolume;
    }

    public static AlcoholByVolume from(Double value) {
        return new AlcoholByVolume(value);
    }

    public Double value() {
        return value;
    }
}
