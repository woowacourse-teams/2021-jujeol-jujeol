package com.jujeol.drink.rds.entity;

import com.jujeol.drink.domain.model.Category;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column(name = "\"key\"")
    private String key;

    @Builder
    public CategoryEntity(Long id, String name, String key) {
        this.id = id;
        this.name = name;
        this.key = key;
    }

    public Category toDomain() {
        return Category.create(id, name, key);
    }
}
