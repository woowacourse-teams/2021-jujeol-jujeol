package com.jujeol.drink.domain;

import com.jujeol.drink.exception.InvalidReviewContentException;
import java.util.Objects;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReviewContent {

    private String content;

    public ReviewContent(String content) {

        if(!validateReviewContent(content)) {
            throw new InvalidReviewContentException();
        }
        this.content = content;
    }

    private boolean validateReviewContent(String content) {
        return !Objects.isNull(content) && !content.isBlank() && content.length() <= 300;
    }
}
