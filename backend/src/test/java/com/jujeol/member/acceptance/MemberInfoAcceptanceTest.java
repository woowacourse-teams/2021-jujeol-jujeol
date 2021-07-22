package com.jujeol.member.acceptance;

import com.jujeol.AcceptanceTest;
import com.jujeol.member.ui.dto.MemberDrinkResponse;
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

}
