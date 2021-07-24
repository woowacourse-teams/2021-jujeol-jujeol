package com.jujeol.member.acceptance;

import static com.jujeol.TestDataLoader.BEERS;
import static com.jujeol.TestDataLoader.MEMBER;
import static com.jujeol.TestDataLoader.REVIEWS;
import static org.assertj.core.api.Assertions.assertThat;

import com.jujeol.AcceptanceTest;
import com.jujeol.drink.domain.Drink;
import com.jujeol.drink.domain.Review;
import com.jujeol.member.application.dto.PreferenceRequest;
import com.jujeol.member.ui.dto.MemberDrinkResponse;
import com.jujeol.member.ui.dto.MemberReviewResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberInfoAcceptanceTest extends AcceptanceTest {

    @DisplayName("내가 마신 술 모아보기 - 성공")
    @Test
    public void showDrinksOfMine() {
        //given
        List<Drink> drinks = BEERS;
        Drink drink1 = drinks.get(0);
        Drink drink2 = drinks.get(1);
        Drink drink3 = drinks.get(2);
        PreferenceRequest preferenceRequest = new PreferenceRequest(3.5);

        request()
                .put("/members/me/drinks/" + drink1.getId() + "/preference", preferenceRequest)
                .withUser()
                .build();

        request()
                .put("/members/me/drinks/" + drink2.getId() + "/preference", preferenceRequest)
                .withUser()
                .build();

        request()
                .put("/members/me/drinks/" + drink3.getId() + "/preference", preferenceRequest)
                .withUser()
                .build();

        //when
        List<MemberDrinkResponse> responses = request().get("/members/me/drinks")
                .withDocument("member/info/drinks")
                .withUser()
                .build()
                .convertBodyToList(MemberDrinkResponse.class);

        //then
        List<Long> expectedIds = List.of(drink1, drink2, drink3)
                .stream()
                .map(Drink::getId)
                .collect(Collectors.toList());

        List<Long> actualIds = responses.stream()
                .map(MemberDrinkResponse::getId)
                .collect(Collectors.toList());

        assertThat(responses).hasSize(3);
        assertThat(actualIds).isEqualTo(expectedIds);
    }

    @DisplayName("내가 남긴 리뷰 모아보기 - 성공")
    @Test
    public void showReviewsOfMine() {
        //given
        List<Review> reviews = REVIEWS.stream()
                .filter(review -> review.getMemberId().equals(MEMBER.getId()))
                .collect(Collectors.toList());

        //when
        List<MemberReviewResponse> responses = request().get("/members/me/reviews")
                .withDocument("member/info/reviews")
                .withUser()
                .build()
                .convertBodyToList(MemberReviewResponse.class);

        //then
        assertThat(responses).hasSize(reviews.size());
    }

}
