package com.jujeol.feedback.domain.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Review {

    private final Long id;
    private final ReviewContent content;
    private final Long drinkId;
    private final Long memberId;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public Review(Long id, ReviewContent content, Long drinkId, Long memberId, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.content = content;
        this.drinkId = drinkId;
        this.memberId = memberId;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static Review create(Long id, ReviewContent content, Long drinkId, Long memberId, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        return new Review(id, content, drinkId, memberId, createdAt, modifiedAt);
    }
}
