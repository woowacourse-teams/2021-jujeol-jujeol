package com.jujeol.member.acceptance;

import static com.jujeol.TestDataLoader.BEERS;
import static com.jujeol.TestDataLoader.MEMBER;
import static com.jujeol.TestDataLoader.REVIEWS;
import static com.jujeol.drink.DrinkTestContainer.*;
import static com.jujeol.member.fixture.TestMember.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.jujeol.AcceptanceTest;
import com.jujeol.RequestBuilder.HttpResponse;
import com.jujeol.admin.acceptance.AdminAcceptanceApi;
import com.jujeol.commons.dto.CommonResponse;
import com.jujeol.commons.dto.PageInfo;
import com.jujeol.drink.DrinkTestContainer;
import com.jujeol.drink.acceptance.DrinkAcceptanceApi;
import com.jujeol.drink.acceptance.ReviewAcceptanceApi;
import com.jujeol.drink.domain.Drink;
import com.jujeol.drink.domain.Review;
import com.jujeol.member.application.dto.PreferenceDto;
import com.jujeol.member.fixture.TestMember;
import com.jujeol.member.ui.dto.MemberDrinkResponse;
import com.jujeol.member.ui.dto.MemberReviewResponse;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MemberInfoAcceptanceTest extends AcceptanceTest {

    @Autowired
    private DrinkAcceptanceApi drinkAcceptanceApi;
    @Autowired
    private AdminAcceptanceApi adminAcceptanceApi;
    @Autowired
    private MemberAcceptanceApi memberAcceptanceApi;
    @Autowired
    private ReviewAcceptanceApi reviewAcceptanceApi;

    @DisplayName("내가 마신 술 모아보기 - 성공")
    @Test
    public void showDrinksOfMine() {
        //given
        final Long obId = 주류_등록(OB);
        final Long stellaId = 주류_등록(STELLA);
        final Long tigerLemonId = 주류_등록(TIGER_LEMON);

        memberAcceptanceApi.선호도_등록(obId, 2.4, CROFFLE);
        memberAcceptanceApi.선호도_등록(stellaId, 2.4, CROFFLE);
        memberAcceptanceApi.선호도_등록(tigerLemonId, 2.4, CROFFLE);

        //when
        List<MemberDrinkResponse> responses = request().get("/members/me/drinks")
                .withDocument("member/info/drinks")
                .withUser(CROFFLE)
                .build()
                .convertBodyToList(MemberDrinkResponse.class);

        //then
        assertThat(responses).hasSize(3);
        assertThat(responses).extracting("id").containsExactlyInAnyOrder(obId, stellaId, tigerLemonId);
    }

    @DisplayName("내가 남긴 리뷰 모아보기 - 성공")
    @Test
    public void showReviewsOfMine() {
        //given
        final Long obId = 주류_등록(OB);
        final Long stellaId = 주류_등록(STELLA);
        final Long appleId = 주류_등록(APPLE);
        String content1 = "크으 맛난다잉~>?";
        String content2 = "워메 맛있는 거~";
        String content3 = "아따! 맛있구마잉";
        reviewAcceptanceApi.리뷰_등록(CROFFLE, content1, obId);
        reviewAcceptanceApi.리뷰_등록(CROFFLE, content2, appleId);
        reviewAcceptanceApi.리뷰_등록(CROFFLE, content3, stellaId);

        //when
        HttpResponse response = request().get("/members/me/reviews")
                .withDocument("member/info/reviews")
                .withUser(CROFFLE)
                .build();

        //then
        페이징_검증(response.pageInfo(), 1,1,3,3);

        final List<MemberReviewResponse> memberReviewResponses = response
                .convertBodyToList(MemberReviewResponse.class);
        assertThat(memberReviewResponses).extracting("content")
                .containsExactlyInAnyOrder(content1, content2, content3);
    }

    private Long 주류_등록(DrinkTestContainer drinkTestContainer) {
        adminAcceptanceApi.어드민_주류_데이터_등록(drinkTestContainer);
        return drinkAcceptanceApi.주류_아이디_조회(drinkTestContainer.getName());
    }
}
