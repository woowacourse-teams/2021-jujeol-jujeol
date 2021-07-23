package com.jujeol.drink.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
            Long id,
            DrinkDto drinkDto,
            String content,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt
    ) {
        return new ReviewDto(id, drinkDto, content, createdAt, modifiedAt);
    }
}
