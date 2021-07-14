package com.jujeol.drink.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "DRINK_ID")
    private Drink drink;

    public static Review from(String content, Drink drink) {
        return new Review(null, content, drink);
    }

    public static Review from(String content) {
        return new Review(null, content, null);
    }

    public void toDrink(Drink drink) {
        this.drink = drink;
    }

    public void editContent(String content) {
        this.content = content;
    }
}
