package com.jujeol.member.acceptance;

import com.jujeol.AcceptanceTest;
import com.jujeol.member.ui.dto.MemberDrinkResponse;
import com.jujeol.member.ui.dto.MemberReviewResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberInfoAcceptanceTest extends AcceptanceTest {

    @DisplayName("내가 마신 술 모아보기 - 성공")
    @Test
    public void showDrinksOfMine() {
        //given
        //when
        List<MemberDrinkResponse> responses = request().get("/members/me/drinks")
                .withDocument("member/info/drinks")
                .withUser()
                .build()
                .convertBodyToList(MemberDrinkResponse.class);

        //then
    }

    @DisplayName("내가 남긴 리뷰 모아보기 - 성공")
    @Test
    public void showReviewsOfMine(){
        //given
        //when
        List<MemberReviewResponse> responses = request().get("/members/me/reviews")
                .withDocument("member/info/reviews")
                .withUser()
                .build()
                .convertBodyToList(MemberReviewResponse.class);

        //then
    }

}
