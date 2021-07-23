package com.jujeol.member.ui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberReviewResponse {

    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    @JsonProperty(value = "drink")
    private ReviewDrinkResponse reviewDrinkResponse;

    public static MemberReviewResponse create(
            Long id,
            String content,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt,
            ReviewDrinkResponse reviewDrinkResponse
    ) {
        return new MemberReviewResponse(id, content, createdAt, modifiedAt, reviewDrinkResponse);
    }
}
