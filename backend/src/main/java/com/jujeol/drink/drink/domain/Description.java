package com.jujeol.drink.drink.domain;

import com.jujeol.drink.drink.exception.InvalidDescriptionException;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Description {

    private static final int LIMIT_LENGTH = 1000;

    @Column(length = LIMIT_LENGTH)
    private String description;

    public Description(String description) {
        if (Objects.isNull(description) || description.isBlank()
                || description.length() > LIMIT_LENGTH) {
            throw new InvalidDescriptionException();
        }
        this.description = description;
    }
}
