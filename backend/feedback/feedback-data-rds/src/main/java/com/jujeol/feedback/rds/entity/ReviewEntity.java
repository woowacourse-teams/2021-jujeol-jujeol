package com.jujeol.feedback.rds.entity;

import com.jujeol.feedback.domain.model.Review;
import com.jujeol.feedback.domain.model.ReviewContent;
import com.jujeol.rds.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 300)
    private String content;

    private Long drinkId;

    private Long memberId;

    @Builder
    public ReviewEntity(Long id, String content, Long drinkId, Long memberId) {
        this.id = id;
        this.content = content;
        this.drinkId = drinkId;
        this.memberId = memberId;
    }

    public Review toDomain() {
        return Review.create(id, ReviewContent.create(content), drinkId, memberId, getCreatedAt(), getModifiedAt());
    }

    public void changeContent(String content) {
        this.content = content;
    }
}
