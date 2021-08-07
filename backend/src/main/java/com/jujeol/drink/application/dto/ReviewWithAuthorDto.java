package com.jujeol.drink.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jujeol.drink.domain.Review;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewWithAuthorDto {

    private Long id;
    @JsonProperty(value = "author")
    private MemberSimpleDto memberSimpleDto;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static ReviewWithAuthorDto create(
            Review review,
            MemberSimpleDto memberSimpleDto
    ) {
        return new ReviewWithAuthorDto(
                review.getId(),
                memberSimpleDto,
                review.getContent(),
                review.getCreatedAt(),
                review.getModifiedAt()
        );
    }
}
