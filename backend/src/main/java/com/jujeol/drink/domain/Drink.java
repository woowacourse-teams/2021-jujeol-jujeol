package com.jujeol.drink.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
public class Drink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;
    @Embedded
    private DrinkName name;
    @Embedded
    private DrinkEnglishName englishName;
    @Embedded
    private AlcoholByVolume alcoholByVolume;
    @Embedded
    private ImageFilePath imageFilePath;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;
    @Column
    private Double preferenceAvg;

    @OneToMany(mappedBy = "drink", fetch = FetchType.EAGER)
    private List<Review> reviews = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "view_count_id")
    private ViewCount viewCount;

    public static Drink create(
            String name,
            String englishName,
            Double alcoholByVolume,
            String imageUrl,
            Double preferenceAvg,
            Category category
    ) {
        return new Drink(
                null,
                new DrinkName(name),
                new DrinkEnglishName(englishName),
                new AlcoholByVolume(alcoholByVolume),
                new ImageFilePath(imageUrl),
                category,
                preferenceAvg,
                new ArrayList<>(),
                ViewCount.create(0L)
        );
    }

    public static Drink create(
            String name,
            String englishName,
            Double alcoholByVolume,
            String imageUrl,
            Double preferenceAvg,
            Category category,
            ViewCount viewCount
    ) {
        return new Drink(
                null,
                new DrinkName(name),
                new DrinkEnglishName(englishName),
                new AlcoholByVolume(alcoholByVolume),
                new ImageFilePath(imageUrl),
                category,
                preferenceAvg,
                new ArrayList<>(),
                viewCount
        );
    }

    public void addReview(Review review) {
        review.toDrink(this);
        reviews.add(review);
    }

    public void removeReview(Review review) {
        reviews.remove(review);
    }

    public void updateViewCount() {
        viewCount.updateViewCount();
    }

    public String getName() {
        return name.getName();
    }

    public String getEnglishName() {
        return englishName.getEnglishName();
    }

    public Double getAlcoholByVolume() {
        return alcoholByVolume.getValue();
    }

    public String getImageFilePath() {
        return imageFilePath.getImageFilePath();
    }

    public Category getCategory() {
        return category;
    }

    public Double getPreferenceAvg() {
        return preferenceAvg;
    }

    public ViewCount getViewCount() {
        return viewCount;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void updateInfo(String name,
            String englishName,
            String imageUrl,
            Category category,
            Double alcoholByVolume) {
        this.name = new DrinkName(name);
        this.englishName = new DrinkEnglishName(englishName);
        this.imageFilePath = new ImageFilePath(imageUrl);
        this.category = category;
        this.alcoholByVolume = new AlcoholByVolume(alcoholByVolume);
    }

    public void updateAverage(Double average) {
        this.preferenceAvg = average;
    }
}
