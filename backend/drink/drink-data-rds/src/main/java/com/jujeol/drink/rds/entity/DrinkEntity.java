package com.jujeol.drink.rds.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DrinkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String englishName;
    private Double alcoholByVolume;
    private String smallImageFilePath;
    private String mediumImageFilePath;
    private String largeImageFilePath;
    private Double preferenceAvg;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @Builder
    public DrinkEntity(Long id, String name, String englishName, Double alcoholByVolume, String smallImageFilePath, String mediumImageFilePath, String largeImageFilePath, Double preferenceAvg, String description, CategoryEntity category) {
        this.id = id;
        this.name = name;
        this.englishName = englishName;
        this.alcoholByVolume = alcoholByVolume;
        this.smallImageFilePath = smallImageFilePath;
        this.mediumImageFilePath = mediumImageFilePath;
        this.largeImageFilePath = largeImageFilePath;
        this.preferenceAvg = preferenceAvg;
        this.description = description;
        this.category = category;
    }
}
