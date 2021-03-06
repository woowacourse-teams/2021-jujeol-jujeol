package com.jujeol.review.ui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jujeol.review.application.dto.ReviewWithAuthorDto;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {

    private Long id;
    @JsonProperty(value = "author")
    private MemberSimpleResponse memberResponse;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static ReviewResponse create(
            ReviewWithAuthorDto reviewWithAuthorDto,
            MemberSimpleResponse memberSimpleResponse
    ) {
        return new ReviewResponse(
                reviewWithAuthorDto.getId(),
                memberSimpleResponse,
                reviewWithAuthorDto.getContent(),
                reviewWithAuthorDto.getCreatedAt(),
                reviewWithAuthorDto.getModifiedAt()
        );
    }
}
