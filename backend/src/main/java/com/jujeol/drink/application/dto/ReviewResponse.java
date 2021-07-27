package com.jujeol.drink.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private MemberDto memberDto;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static ReviewResponse create(
            Long id,
            MemberDto memberDto,
            String content,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt
    ) {
        return new ReviewResponse(id, memberDto, content, createdAt, modifiedAt);
    }
}
