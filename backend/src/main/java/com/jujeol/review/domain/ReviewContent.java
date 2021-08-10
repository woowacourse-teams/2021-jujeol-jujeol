package com.jujeol.review.domain;

import com.jujeol.review.exception.InvalidReviewContentException;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReviewContent {

    @Column(nullable = false, length = 300)
    private String content;

    public ReviewContent(String content) {

        if (!validateReviewContent(content)) {
            throw new InvalidReviewContentException();
        }
        this.content = content;
    }

    private boolean validateReviewContent(String content) {
        return !Objects.isNull(content) && !content.isBlank() && content.length() <= 300;
    }
}
