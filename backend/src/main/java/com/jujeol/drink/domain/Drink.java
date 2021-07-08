package com.jujeol.drink.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Drink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private DrinkName name;
    @Embedded
    private AlcoholByVolume alcoholByVolume;
    private String imageUrl;

    public Drink(String name, Double alcoholByVolume, String imageUrl) {
        this.name = new DrinkName(name);
        this.alcoholByVolume = new AlcoholByVolume(alcoholByVolume);
        this.imageUrl = imageUrl;
    }
}
