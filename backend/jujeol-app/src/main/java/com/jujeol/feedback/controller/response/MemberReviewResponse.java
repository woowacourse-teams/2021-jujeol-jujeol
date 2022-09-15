package com.jujeol.feedback.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class MemberReviewResponse {

    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private DrinkResponse drink;

    @AllArgsConstructor
    @Getter
    public static class DrinkResponse {
        private Long drinkId;
        private String name;
        private String imageUrl;
    }
}
