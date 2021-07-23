package com.jujeol.member.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReviewDrinkResponse {

    private Long drinkId;
    private String name;
    private String imageUrl;

    public static ReviewDrinkResponse create(
            Long id,
            String name,
            String imageUrl
    ) {
        return new ReviewDrinkResponse(id, name, imageUrl);
    }
}
