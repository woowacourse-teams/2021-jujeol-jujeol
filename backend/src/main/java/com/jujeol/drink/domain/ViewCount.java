package com.jujeol.drink.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
@Table(name = "view_count")
public class ViewCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long viewCount;
    @OneToOne(mappedBy = "viewCount")
    private Drink drink;

    public static ViewCount create(Long viewCount) {
        return new ViewCount(null, viewCount, null);
    }

    public Long getId() {
        return id;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public Drink getDrink() {
        return drink;
    }

    public void updateViewCount() {
        viewCount += 1;
    }
}
