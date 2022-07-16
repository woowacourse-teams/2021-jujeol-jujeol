package com.jujeol.feedback.rds.entity;

import com.jujeol.feedback.domain.model.Review;
import com.jujeol.feedback.domain.model.ReviewContent;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewEntity {

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

    // TODO : modifiedAt, createdAt Audit 추가
    public Review toDomain() {
        return Review.create(id, ReviewContent.create(content), drinkId, memberId, null, null);
    }
}
