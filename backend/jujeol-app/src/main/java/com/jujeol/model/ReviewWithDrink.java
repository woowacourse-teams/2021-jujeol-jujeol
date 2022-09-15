package com.jujeol.model;

import com.jujeol.drink.domain.model.Drink;
import com.jujeol.feedback.domain.model.Review;
import lombok.Getter;

@Getter
public class ReviewWithDrink {

    private final Drink drink;
    private final Review review;

    private ReviewWithDrink(Drink drink, Review review) {
        this.drink = drink;
        this.review = review;
    }

    public static ReviewWithDrink create(Drink drink, Review review) {
        return new ReviewWithDrink(drink, review);
    }
}
