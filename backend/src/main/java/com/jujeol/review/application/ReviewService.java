package com.jujeol.review.application;

import com.jujeol.drink.drink.domain.repository.DrinkRepository;
import com.jujeol.member.member.domain.repository.MemberRepository;
import com.jujeol.preference.domain.repository.PreferenceRepository;
import com.jujeol.review.application.dto.MemberSimpleDto;
import com.jujeol.review.application.dto.ReviewWithAuthorDto;
import com.jujeol.review.domain.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final DrinkRepository drinkRepository;
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final PreferenceRepository preferenceRepository;



    public Page<ReviewWithAuthorDto> showReviews(Long drinkId, Pageable pageable) {
        return reviewRepository.findAllByDrinkId(drinkId, pageable)
                .map(review -> ReviewWithAuthorDto.create(
                        review,
                        MemberSimpleDto.create(review.getMember())
                        )
                );
    }
}
