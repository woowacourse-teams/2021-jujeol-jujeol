package com.jujeol.member.acceptance;

import static com.jujeol.drink.DrinkTestContainer.APPLE;
import static com.jujeol.drink.DrinkTestContainer.OB;
import static com.jujeol.drink.DrinkTestContainer.STELLA;
import static com.jujeol.drink.DrinkTestContainer.TIGER_LEMON;
import static com.jujeol.member.fixture.TestMember.CROFFLE;
import static org.assertj.core.api.Assertions.assertThat;

import com.jujeol.AcceptanceTest;
import com.jujeol.RequestBuilder.HttpResponse;
import com.jujeol.admin.acceptance.AdminAcceptanceTool;
import com.jujeol.drink.DrinkTestContainer;
import com.jujeol.drink.acceptance.DrinkAcceptanceTool;
import com.jujeol.review.acceptance.ReviewAcceptanceTool;
import com.jujeol.member.member.ui.dto.MemberDrinkResponse;
import com.jujeol.member.member.ui.dto.MemberReviewResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MemberInfoAcceptanceTest extends AcceptanceTest {

    @Autowired
    private DrinkAcceptanceTool drinkAcceptanceTool;
    @Autowired
    private AdminAcceptanceTool adminAcceptanceTool;
    @Autowired
    private MemberAcceptanceTool memberAcceptanceTool;
    @Autowired
    private ReviewAcceptanceTool reviewAcceptanceTool;

    @DisplayName("내가 마신 술 모아보기 - 성공")
    @Test
    public void showDrinksOfMine() {
        //given
        final Long obId = 주류_등록(OB);
        final Long stellaId = 주류_등록(STELLA);
        final Long tigerLemonId = 주류_등록(TIGER_LEMON);

        memberAcceptanceTool.선호도_등록(obId, 2.4, CROFFLE);
        memberAcceptanceTool.선호도_등록(stellaId, 3.0, CROFFLE);
        memberAcceptanceTool.선호도_등록(tigerLemonId, 4.5, CROFFLE);

        //when
        List<MemberDrinkResponse> responses = request().get("/members/me/drinks")
                .withDocument("member/info/drinks")
                .withUser(CROFFLE)
                .build()
                .convertBodyToList(MemberDrinkResponse.class);

        //then
        assertThat(responses).hasSize(3);
        assertThat(responses).extracting("id")
                .containsExactlyInAnyOrder(obId, stellaId, tigerLemonId);
        assertThat(responses).extracting("preferenceRate").containsExactlyInAnyOrder(2.4, 3.0, 4.5);
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
        reviewAcceptanceTool.리뷰_등록(CROFFLE, content1, obId);
        reviewAcceptanceTool.리뷰_등록(CROFFLE, content2, appleId);
        reviewAcceptanceTool.리뷰_등록(CROFFLE, content3, stellaId);

        //when
        HttpResponse response = request().get("/members/me/reviews")
                .withDocument("member/info/reviews")
                .withUser(CROFFLE)
                .build();

        //then
        페이징_검증(response.pageInfo(), 1, 1, 3, 3);

        final List<MemberReviewResponse> memberReviewResponses = response
                .convertBodyToList(MemberReviewResponse.class);
        assertThat(memberReviewResponses).extracting("content")
                .containsExactlyInAnyOrder(content1, content2, content3);
    }

    private Long 주류_등록(DrinkTestContainer drinkTestContainer) {
        adminAcceptanceTool.어드민_주류_데이터_등록(drinkTestContainer);
        return drinkAcceptanceTool.주류_아이디_조회(drinkTestContainer.getName());
    }
}
