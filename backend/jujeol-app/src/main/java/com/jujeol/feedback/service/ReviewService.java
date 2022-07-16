package com.jujeol.feedback.service;

import com.jujeol.drink.domain.reader.DrinkReader;
import com.jujeol.feedback.domain.model.Review;
import com.jujeol.feedback.rds.repository.ReviewPageRepository;
import com.jujeol.member.domain.model.Member;
import com.jujeol.member.domain.reader.MemberReader;
import com.jujeol.model.ReviewWithDrink;
import com.jujeol.model.ReviewWithMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Component
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewPageRepository reviewPageRepository;
    
    private final DrinkReader drinkReader;
    private final MemberReader memberReader;

    @Transactional(readOnly = true)
    public Page<ReviewWithDrink> findMyReviews(Long memberId, Pageable pageable) {
        Page<Review> reviews = reviewPageRepository.findByMemberId(memberId, pageable);

        Pageable resultPageable = reviews.getPageable();
        long totalElements = reviews.getTotalElements();
        Map<Long, List<Review>> reviewByDrinkId = reviews.stream().collect(groupingBy(Review::getDrinkId));

        List<ReviewWithDrink> contents = drinkReader.findAllByDrinkIdIn(reviewByDrinkId.keySet())
            .stream()
            .flatMap(drink -> reviewByDrinkId.get(drink.getDrinkId())
                .stream()
                .map(review -> ReviewWithDrink.create(drink, review)))
            .collect(Collectors.toList());

        return new PageImpl<>(contents, resultPageable, totalElements);
    }

    @Transactional(readOnly = true)
    public Page<ReviewWithMember> findReviewWithMember(Long drinkId, Pageable pageable) {
        Page<Review> reviews = reviewPageRepository.findByDrinkId(drinkId, pageable);
        List<Long> memberIds = reviews.stream().map(Review::getMemberId).distinct().collect(Collectors.toList());

        Map<Long, Member> memberById = new HashMap<>();
        memberReader.findAllByIdsIn(memberIds).forEach(member -> memberById.put(member.getId(), member));

        return reviews.map(review -> ReviewWithMember.create(review, memberById.get(review.getMemberId())));
    }
}
