package com.jujeol.review.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jujeol.drink.drink.application.dto.DrinkDto;
import com.jujeol.review.domain.Review;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    private Long id;
    @JsonProperty(value = "author")
    private DrinkDto drinkDto;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static ReviewDto create(
            Review review,
            DrinkDto drinkDto
    ) {
        return new ReviewDto(
                review.getId(),
                drinkDto,
                review.getContent(),
                review.getCreatedAt(),
                review.getModifiedAt()
        );
    }
}
