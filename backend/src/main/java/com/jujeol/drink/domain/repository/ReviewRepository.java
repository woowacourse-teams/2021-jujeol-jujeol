package com.jujeol.drink.domain.repository;

import com.jujeol.drink.domain.Review;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findAllByDrinkId(Long drinkId, Pageable pageable);

    @Query("select r from Review r where r.drink.id = :drinkId and r.member.id = :memberId order by r.createdAt desc")
    List<Review> findByDrinkIdAndMemberId(Long drinkId, Long memberId, Pageable pageable);

    @Query("select r from Review  r where r.member.id = :memberId order by r.createdAt desc")
    Page<Review> findUsingMemberIdOrderByCreatedAtDesc(Long memberId, Pageable pageable);
}
