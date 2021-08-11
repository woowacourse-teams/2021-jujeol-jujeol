package com.jujeol.review.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCreateDto {

    private String content;
    private Long drinkId;

    public static ReviewCreateDto create(String content, Long drinkId) {
        return new ReviewCreateDto(content, drinkId);
    }
}