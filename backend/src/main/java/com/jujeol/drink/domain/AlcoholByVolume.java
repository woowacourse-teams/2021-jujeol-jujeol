package com.jujeol.drink.domain;

import com.jujeol.drink.exception.InvalidAlcoholByVolumeException;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class AlcoholByVolume {

    @Column
    private Double value;

    public AlcoholByVolume(Double alcoholByVolume) {
        if (Objects.isNull(alcoholByVolume)) {
            return;
        }

        if (alcoholByVolume < 0 || alcoholByVolume > 100) {
            throw new InvalidAlcoholByVolumeException();
        }
        this.value = alcoholByVolume;
    }
}
