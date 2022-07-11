package com.jujeol.drink.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberDrinkResponse {

    private Long id;
    private String name;
    private String imageUrl;
    private double preferenceRate;
}
