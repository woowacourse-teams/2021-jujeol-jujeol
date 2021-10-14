package com.jujeol.elasticsearch.acceptance;

import static com.jujeol.drink.DrinkTestContainer.APPLE;
import static com.jujeol.drink.DrinkTestContainer.ESTP;
import static com.jujeol.drink.DrinkTestContainer.KGB;
import static com.jujeol.drink.DrinkTestContainer.OB;
import static com.jujeol.drink.DrinkTestContainer.STELLA;
import static com.jujeol.drink.DrinkTestContainer.TIGER_LEMON;
import static com.jujeol.drink.DrinkTestContainer.TIGER_RAD;
import static com.jujeol.drink.DrinkTestContainer.TSINGTAO;
import static org.assertj.core.api.Assertions.assertThat;

import com.jujeol.AcceptanceTest;
import com.jujeol.RequestBuilder.HttpResponse;
import com.jujeol.admin.acceptance.AdminAcceptanceTool;
import com.jujeol.drink.DrinkTestContainer;
import com.jujeol.drink.acceptance.DrinkAcceptanceTool;
import com.jujeol.drink.drink.ui.dto.DrinkResponse;
import com.jujeol.member.acceptance.MemberAcceptanceTool;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class DrinkDocumentAcceptanceTest extends AcceptanceTest {

    @Autowired
    private AdminAcceptanceTool adminAcceptanceTool;
    @Autowired
    private DrinkAcceptanceTool drinkAcceptanceTool;
    @Autowired
    private MemberAcceptanceTool memberAcceptanceTool;

    List<DrinkTestContainer> drinks = new ArrayList<>();

    @BeforeEach
    void setUp() {
        adminAcceptanceTool.어드민_주류_데이터_등록(KGB, STELLA, APPLE, ESTP, OB, TIGER_LEMON, TIGER_RAD, TSINGTAO);
        drinks = List.of(KGB, STELLA, APPLE, ESTP, OB, TIGER_LEMON, TIGER_RAD, TSINGTAO);
    }

    @BeforeEach
    void close() {
    }

    @DisplayName("검색 조회 - 성공")
    @Test
    public void showDrinksBySearchTest() {
        //given
        String search = "OB";
        int page = 1;

        //when
        final HttpResponse httpResponse = request()
                .get("/search?keyword=" + search + "&page=" + page)
                .withDocument("drinks/show/search")
                .build();

        //then
        final List<DrinkResponse> drinkSimpleResponses =
                httpResponse.convertBodyToList(DrinkResponse.class);

        assertThat(drinkSimpleResponses.get(0).getName()).isEqualTo(OB.getName());

        페이징_검증(httpResponse.pageInfo(), 1, 1, 10, 1);
    }
}
