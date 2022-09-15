package com.jujeol.drink.rds.entity;

import com.jujeol.drink.domain.model.AlcoholByVolume;
import com.jujeol.drink.domain.model.Category;
import com.jujeol.drink.domain.model.Description;
import com.jujeol.drink.domain.model.Drink;
import com.jujeol.drink.domain.model.DrinkEnglishName;
import com.jujeol.drink.domain.model.DrinkName;
import com.jujeol.drink.domain.model.ImageFilePath;
import com.jujeol.rds.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DrinkEntity extends BaseEntity {
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
    @Column(length = 1000)
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

    public Drink toDomain() {
        return Drink.builder()
            .drinkId(id)
            .name(DrinkName.from(name))
            .englishName(DrinkEnglishName.from(englishName))
            .alcoholByVolume(AlcoholByVolume.from(alcoholByVolume))
            .description(Description.from(description))
            .category(Category.create(category.getId(), category.getName(), category.getKey()))
            .imageFilePath(ImageFilePath.create(smallImageFilePath, mediumImageFilePath, largeImageFilePath))
            .preferenceAvg(preferenceAvg)
            .build();
    }

    public void changePreferenceAvg(Double preferenceAvg) {
        this.preferenceAvg = preferenceAvg;
    }
}
