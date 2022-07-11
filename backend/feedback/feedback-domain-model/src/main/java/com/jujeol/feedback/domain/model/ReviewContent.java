package com.jujeol.feedback.domain.model;

import com.jujeol.feedback.domain.exception.InvalidReviewContentException;

import java.util.Objects;

public class ReviewContent {

    private String content;

    private ReviewContent(String content) {
        if (!validateReviewContent(content)) {
            throw new InvalidReviewContentException();
        }
        this.content = content;
    }

    public static ReviewContent create(String content) {
        return new ReviewContent(content);
    }

    private boolean validateReviewContent(String content) {
        return !Objects.isNull(content) && !content.isBlank() && content.length() <= 300;
    }

    public String value() {
        return content;
    }
}
