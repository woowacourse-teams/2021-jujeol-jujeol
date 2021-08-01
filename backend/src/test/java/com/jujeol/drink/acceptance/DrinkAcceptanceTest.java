package com.jujeol.drink.acceptance;

import static com.jujeol.commons.exception.ExceptionCodeAndDetails.NOT_FOUND_DRINK;
import static com.jujeol.drink.DrinkTestContainer.APPLE;
import static com.jujeol.drink.DrinkTestContainer.ESTP;
import static com.jujeol.drink.DrinkTestContainer.KGB;
import static com.jujeol.drink.DrinkTestContainer.OB;
import static com.jujeol.drink.DrinkTestContainer.STELLA;
import static com.jujeol.drink.DrinkTestContainer.TIGER_LEMON;
import static com.jujeol.drink.DrinkTestContainer.TIGER_RAD;
import static com.jujeol.drink.DrinkTestContainer.TSINGTAO;
import static com.jujeol.drink.DrinkTestContainer.asNames;
import static com.jujeol.member.fixture.TestMember.CROFFLE;
import static com.jujeol.member.fixture.TestMember.PIKA;
import static com.jujeol.member.fixture.TestMember.SOLONG;
import static com.jujeol.member.fixture.TestMember.WEDGE;
import static org.assertj.core.api.Assertions.assertThat;

import com.jujeol.AcceptanceTest;
import com.jujeol.RequestBuilder.HttpResponse;
import com.jujeol.admin.acceptance.AdminAcceptanceTool;
import com.jujeol.commons.exception.JujeolExceptionDto;
import com.jujeol.drink.DrinkTestContainer;
import com.jujeol.drink.ui.dto.DrinkDetailResponse;
import com.jujeol.drink.ui.dto.DrinkSimpleResponse;
import com.jujeol.member.acceptance.MemberAcceptanceTool;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DrinkAcceptanceTest extends AcceptanceTest {

    @Autowired
    private AdminAcceptanceTool adminAcceptanceTool;
    @Autowired
    private DrinkAcceptanceTool drinkAcceptanceTool;
    @Autowired
    private MemberAcceptanceTool memberAcceptanceTool;

    @BeforeEach
    void setUp() {
        adminAcceptanceTool
                .어드민_주류_데이터_등록(KGB, STELLA, APPLE, ESTP, OB, TIGER_LEMON, TIGER_RAD, TSINGTAO);
    }

    @DisplayName("추천 조회 - 성공(비로그인 시 선호도 순서)")
    @Test
    public void showRecommendDrinksTest() {
        //when
        final HttpResponse httpResponse = request()
                .get("/drinks")
                .withDocument("drinks/show/all")
                .build();

        //then
        final List<String> drinkNames =
                asNames(KGB, STELLA, APPLE, ESTP, OB, TIGER_LEMON, TIGER_RAD, TSINGTAO);

        assertThat(httpResponse.convertBodyToList(DrinkSimpleResponse.class))
                .extracting("name")
                .containsExactlyElementsOf(drinkNames);

        페이징_검증(httpResponse.pageInfo(), 1, 1, 10, 8);
    }

    @DisplayName("추천 조회 - 성공(로그인 시 협업필터링)")
    @Test
    public void showDrinksByUserPreferenceTest() {
        //given
        협업_필터링_데이터_등록();
        String theme = "preference";

        //when
        List<DrinkSimpleResponse> drinkSimpleResponses = request()
                .get("/drinks/recommendation")
                .withDocument("drinks/show/recommend")
                .withUser(PIKA)
                .build().convertBodyToList(DrinkSimpleResponse.class);

        //then
        assertThat(drinkSimpleResponses.get(0).getName()).isEqualTo("애플");
        assertThat(drinkSimpleResponses.get(1).getName()).isEqualTo("타이거 라들러 레몬");
        assertThat(drinkSimpleResponses.get(2).getName()).isEqualTo("타이거 라들러 자몽");
    }

    private void 협업_필터링_데이터_등록() {
        final Long obId = drinkAcceptanceTool.주류_아이디_조회(OB.getName());
        final Long stellaId = drinkAcceptanceTool.주류_아이디_조회(STELLA.getName());
        final Long kgbId = drinkAcceptanceTool.주류_아이디_조회(KGB.getName());
        final Long tigerId = drinkAcceptanceTool.주류_아이디_조회(TIGER_LEMON.getName());
        final Long appleId = drinkAcceptanceTool.주류_아이디_조회(APPLE.getName());
        final Long tigerRadId = drinkAcceptanceTool.주류_아이디_조회(TIGER_RAD.getName());
        final Long tsingatoId = drinkAcceptanceTool.주류_아이디_조회(TSINGTAO.getName());

        memberAcceptanceTool.선호도_등록(obId, 2.0, PIKA);
        memberAcceptanceTool.선호도_등록(stellaId, 5.0, PIKA);
        memberAcceptanceTool.선호도_등록(kgbId, 4.5, PIKA);

        memberAcceptanceTool.선호도_등록(obId, 1.5, SOLONG);
        memberAcceptanceTool.선호도_등록(stellaId, 4.5, SOLONG);
        memberAcceptanceTool.선호도_등록(kgbId, 4.2, SOLONG);
        memberAcceptanceTool.선호도_등록(tigerId, 5.0, SOLONG);
        memberAcceptanceTool.선호도_등록(appleId, 4.5, SOLONG);
        memberAcceptanceTool.선호도_등록(tigerRadId, 4.5, SOLONG);

        memberAcceptanceTool.선호도_등록(obId, 1.0, WEDGE);
        memberAcceptanceTool.선호도_등록(kgbId, 4.5, WEDGE);
        memberAcceptanceTool.선호도_등록(tigerId, 4.7, WEDGE);
        memberAcceptanceTool.선호도_등록(appleId, 4.5, WEDGE);
        memberAcceptanceTool.선호도_등록(tigerRadId, 4.5, WEDGE);

        memberAcceptanceTool.선호도_등록(obId, 4.7, CROFFLE);
        memberAcceptanceTool.선호도_등록(kgbId, 1.5, CROFFLE);
        memberAcceptanceTool.선호도_등록(stellaId, 2.4, CROFFLE);
        memberAcceptanceTool.선호도_등록(tigerId, 2.1, CROFFLE);
        memberAcceptanceTool.선호도_등록(tsingatoId, 1.1, CROFFLE);
    }

    @DisplayName("검색 조회 - 성공")
    @Test
    public void showDrinksBySearchTest() {
        //given
        String search = "ob";
        String category = "beer";
        int page = 1;
        //when
        final HttpResponse httpResponse = request()
                .get("/drinks?search=" + search + "&category=" + category + "&page=" + page)
                .withDocument("drinks/show/search")
                .build();

        //then
        final List<DrinkSimpleResponse> drinkSimpleResponses =
                httpResponse.convertBodyToList(DrinkSimpleResponse.class);

        assertThat(drinkSimpleResponses).extracting("name").contains(OB.getName());

        페이징_검증(httpResponse.pageInfo(), 1, 1, 10, 1);
    }

    @DisplayName("검색 조회(검색어 일부만 존재) - 성공")
    @Test
    public void showDrinksBySearchWithoutCategoryTest() {
        //given
        String search = "ste";
        int page = 1;
        //when
        final HttpResponse httpResponse = request()
                .get("/drinks?search=" + search + "&page=" + page)
                .build();

        //then
        final List<DrinkSimpleResponse> drinkSimpleResponses =
                httpResponse.convertBodyToList(DrinkSimpleResponse.class);

        assertThat(drinkSimpleResponses).extracting("name").contains(STELLA.getName());

        페이징_검증(httpResponse.pageInfo(), 1, 1, 10, 1);
    }

    @DisplayName("단일 조회 - 성공")
    @Test
    public void showDrinkDetailTest() {
        //given
        final Long obId = 주류_아이디(OB);
        //when
        DrinkDetailResponse drinkDetailResponse = request()
                .get("/drinks/{id}", obId)
                .withDocument("drinks/show/detail")
                .withUser()
                .build()
                .convertBody(DrinkDetailResponse.class);

        //then
        assertThat(drinkDetailResponse.getName()).isEqualTo(OB.getName());
    }

    @DisplayName("단일 조회 - 실패 (찾을 수 없는 id)")
    @Test
    public void showDrinkDetailTest_fail() {
        //when
        final JujeolExceptionDto errorResponse = request()
                .get("drinks/{id}", Long.MAX_VALUE)
                .withDocument("drinks/show/detail-fail")
                .build().errorResponse();

        //then
        예외_검증(errorResponse, NOT_FOUND_DRINK);
    }

    private Long 주류_아이디(DrinkTestContainer drinkTestContainer) {
        return drinkAcceptanceTool.주류_아이디_조회(drinkTestContainer.getName());
    }
}
