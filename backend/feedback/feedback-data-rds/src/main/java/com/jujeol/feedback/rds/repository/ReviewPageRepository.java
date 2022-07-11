package com.jujeol.feedback.rds.repository;

import com.jujeol.feedback.domain.model.Review;
import com.jujeol.feedback.rds.entity.ReviewEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Deprecated
@Component
@RequiredArgsConstructor
public class ReviewPageRepository {

    private final ReviewJpaRepository reviewJpaRepository;

    public Page<Review> findByMemberId(Long memberId, Pageable pageable) {
        return reviewJpaRepository.findByMemberId(memberId, pageable).map(ReviewEntity::toDomain);
    }
}
