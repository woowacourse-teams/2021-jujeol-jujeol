package com.jujeol.feedback.rds.repository;

import com.jujeol.feedback.domain.model.Review;
import com.jujeol.feedback.domain.reader.ReviewReader;
import com.jujeol.feedback.domain.usecase.ReviewModifyUseCase;
import com.jujeol.feedback.domain.usecase.ReviewRegisterUseCase;
import com.jujeol.feedback.rds.entity.ReviewEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReviewDomainRepository
    implements ReviewReader,

    ReviewRegisterUseCase.ReviewPort,
    ReviewModifyUseCase.ReviewPort {

    private final ReviewJpaRepository reviewJpaRepository;

    @Transactional(readOnly = true)
    public List<Review> findByDrinkIdAndMemberId(Long drinkId, Long memberId) {
        return reviewJpaRepository.findByDrinkIdAndMemberId(drinkId, memberId).stream().map(ReviewEntity::toDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void insert(Long memberId, Long drinkId, String content) {
        reviewJpaRepository.save(
            ReviewEntity.builder()
                .drinkId(drinkId)
                .memberId(memberId)
                .content(content)
                .build()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Review> findById(Long id) {
        return reviewJpaRepository.findById(id).map(ReviewEntity::toDomain);
    }

    @Override
    @Transactional
    public void update(Long reviewId, String content) {
        reviewJpaRepository.findById(reviewId)
            .orElseThrow()
            .changeContent(content);
    }
}
