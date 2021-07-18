package com.jujeol.drink.domain;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findAllByDrinkId(Long drinkId, Pageable pageable);

    @Query("select r from Review r where r.drink.id = :drinkId and r.member.id = :memberId order by r.createAt desc")
    List<Review> findByDrinkIdAndMemberId(Long drinkId, Long memberId, Pageable pageable);
}
