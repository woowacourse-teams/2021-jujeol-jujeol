package com.jujeol.review.ui.dto;

import com.jujeol.review.application.dto.ReviewCreateDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCreateRequest {

    private String content;
    private Long drinkId;

    public ReviewCreateDto toDto() {
        return ReviewCreateDto.create(content, drinkId);
    }
}