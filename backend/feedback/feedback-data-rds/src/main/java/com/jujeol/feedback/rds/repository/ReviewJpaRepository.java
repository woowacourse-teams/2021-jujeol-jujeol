package com.jujeol.feedback.rds.repository;

import com.jujeol.feedback.rds.entity.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewJpaRepository extends JpaRepository<ReviewEntity, Long> {

    Page<ReviewEntity> findByMemberId(Long memberId, Pageable pageable);
}
