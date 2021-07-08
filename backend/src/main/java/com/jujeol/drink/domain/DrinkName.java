package com.jujeol.drink.domain;

import com.jujeol.drink.exception.InvalidDrinkNameException;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
class DrinkName {

    @Column
    private String name;

    public DrinkName(String name) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new InvalidDrinkNameException();
        }
    }
}
