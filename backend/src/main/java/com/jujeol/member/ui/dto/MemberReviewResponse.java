package com.jujeol.member.ui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jujeol.drink.application.dto.ReviewDto;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class MemberReviewResponse {

    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    @JsonProperty(value = "drink")
    private ReviewDrinkResponse reviewDrinkResponse;

    public static MemberReviewResponse create(
            ReviewDto reviewDto,
            ReviewDrinkResponse reviewDrinkResponse
    ) {
        return new MemberReviewResponse(
                reviewDto.getId(),
                reviewDto.getContent(),
                reviewDto.getCreatedAt(),
                reviewDto.getModifiedAt(),
                reviewDrinkResponse);
    }
}
