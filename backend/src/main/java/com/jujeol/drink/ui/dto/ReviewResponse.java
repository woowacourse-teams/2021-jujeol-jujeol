package com.jujeol.drink.ui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jujeol.drink.application.dto.ReviewWithAuthorDto;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
