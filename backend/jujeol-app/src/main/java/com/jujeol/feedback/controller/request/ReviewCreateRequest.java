package com.jujeol.feedback.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewCreateRequest {

    private String content;
    private Long drinkId;

    @Builder
    public ReviewCreateRequest(String content, Long drinkId) {
        this.content = content;
        this.drinkId = drinkId;
    }
}
