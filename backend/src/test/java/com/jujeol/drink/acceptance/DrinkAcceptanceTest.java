package com.jujeol.drink.acceptance;

import static com.jujeol.commons.exception.ExceptionCodeAndDetails.INVALID_SORT_BY;
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
import com.jujeol.admin.acceptance.AdminAcceptanceTool;
import com.jujeol.commons.exception.JujeolExceptionDto;
import com.jujeol.drink.DrinkTestContainer;
import com.jujeol.drink.drink.ui.dto.DrinkResponse;
import com.jujeol.member.acceptance.MemberAcceptanceTool;
import com.jujeol.testtool.response.HttpResponse;
import java.util.ArrayList;
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

    List<DrinkTestContainer> drinks = new ArrayList<>();

    @BeforeEach
    void setUp() {
        adminAcceptanceTool.어드민_주류_데이터_등록(KGB, STELLA, APPLE, ESTP, OB, TIGER_LEMON, TIGER_RAD, TSINGTAO);
        drinks = List.of(KGB, STELLA, APPLE, ESTP, OB, TIGER_LEMON, TIGER_RAD, TSINGTAO);

    }

    @DisplayName("메인 페이지 - 예상 선호도 순서(비로그인)")
    @Test
    public void showDrinksSortByExpectedPreferenceWithoutLoginTest() {
        // given
        final Long obId = drinkAcceptanceTool.주류_아이디_조회(OB.getName());
        final Long stellaId = drinkAcceptanceTool.주류_아이디_조회(STELLA.getName());
        final Long kgbId = drinkAcceptanceTool.주류_아이디_조회(KGB.getName());
        final Long tigerId = drinkAcceptanceTool.주류_아이디_조회(TIGER_LEMON.getName());
        final Long appleId = drinkAcceptanceTool.주류_아이디_조회(APPLE.getName());
        final Long tigerRadId = drinkAcceptanceTool.주류_아이디_조회(TIGER_RAD.getName());
        final Long tsingatoId = drinkAcceptanceTool.주류_아이디_조회(TSINGTAO.getName());
        final Long estpId = drinkAcceptanceTool.주류_아이디_조회(ESTP.getName());

        memberAcceptanceTool.선호도_등록(obId, 5.0, PIKA);
        memberAcceptanceTool.선호도_등록(stellaId, 4.5, PIKA);
        memberAcceptanceTool.선호도_등록(kgbId, 4.0, PIKA);
        memberAcceptanceTool.선호도_등록(tigerId, 3.5, PIKA);
        memberAcceptanceTool.선호도_등록(appleId, 3.0, PIKA);
        memberAcceptanceTool.선호도_등록(tigerRadId, 2.5, PIKA);
        memberAcceptanceTool.선호도_등록(tsingatoId, 2.0, PIKA);
        memberAcceptanceTool.선호도_등록(estpId, 1.0, PIKA);

        // when
        final HttpResponse httpResponse = request()
                .get("/drinks?sortBy=expectPreference&page=0&size=7")
                .withDocument("drinks/show/sortByExpectPreferenceWithoutLogin")
                .build();

        //then
        final List<String> drinkNames =
                asNames(OB, STELLA, KGB, TIGER_LEMON, APPLE, TIGER_RAD, TSINGTAO);

        assertThat(httpResponse.convertBodyToList(DrinkResponse.class))
                .extracting("name")
                .containsExactlyElementsOf(drinkNames);

        페이징_검증(httpResponse.pageInfo(), 1, 1, 7, 7);
    }

    @DisplayName("메인 페이지 - 예상 선호도 순서(로그인)")
    @Test
    public void showDrinksByUserPreferenceTest() {
        //given
        협업_필터링_데이터_등록();

        //when
        List<DrinkResponse> drinkSimpleResponses = request()
                .get("/drinks?sortBy=expectPreference&page=0&size=7")
                .withDocument("drinks/show/sortByExpectPreferenceWithLogin")
                .withUser(PIKA)
                .build().convertBodyToList(DrinkResponse.class);

        //then
        assertThat(drinkSimpleResponses)
                .extracting("name")
                .containsExactlyInAnyOrder("타이거 라들러 레몬","스텔라", "타이거 라들러 자몽",
                        "애플", "KGB", "ESTP", "칭따오");
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
        memberAcceptanceTool.선호도_등록(stellaId, 5.0, WEDGE);
        memberAcceptanceTool.선호도_등록(tigerId, 4.7, WEDGE);
        memberAcceptanceTool.선호도_등록(appleId, 4.5, WEDGE);
        memberAcceptanceTool.선호도_등록(tigerRadId, 4.5, WEDGE);

        memberAcceptanceTool.선호도_등록(obId, 4.7, CROFFLE);
        memberAcceptanceTool.선호도_등록(kgbId, 1.5, CROFFLE);
        memberAcceptanceTool.선호도_등록(stellaId, 2.4, CROFFLE);
        memberAcceptanceTool.선호도_등록(tigerId, 2.1, CROFFLE);
        memberAcceptanceTool.선호도_등록(tsingatoId, 1.1, CROFFLE);
    }

    @DisplayName("메인 페이지 - 선호도 기준으로 전체 조회")
    @Test
    void showDrinkAllSortByPreference() {

        final int page = 1;
        final String sortBy = "preferenceAvg";

        final Long obId = drinkAcceptanceTool.주류_아이디_조회(OB.getName());
        final Long stellaId = drinkAcceptanceTool.주류_아이디_조회(STELLA.getName());

        memberAcceptanceTool.선호도_등록(obId, 4.0, PIKA);
        memberAcceptanceTool.선호도_등록(stellaId, 3.0, PIKA);

        //given
        HttpResponse httpResponse = request()
                .get("/drinks?page=" + page + "&sortBy=" + sortBy)
                .withDocument("drinks/show/allSortByPreference")
                .build();
        List<DrinkResponse> drinkResponses = httpResponse.convertBodyToList(DrinkResponse.class);
        //when
        //then
        페이징_검증(httpResponse.pageInfo(), 1, 1, 10, 8);
        assertThat(drinkResponses.get(0).getId()).isEqualTo(obId);
        assertThat(drinkResponses.get(1).getId()).isEqualTo(stellaId);
    }

    @DisplayName("메인 페이지 - 선호도 기준으로 맥주 조회")
    @Test
    void showDrinkCategorySortByPreference() {

        final String categoryKey = "BEER";
        final int page = 1;
        final String sortBy = "preferenceAvg";

        final Long obId = drinkAcceptanceTool.주류_아이디_조회(OB.getName());
        final Long stellaId = drinkAcceptanceTool.주류_아이디_조회(STELLA.getName());
        memberAcceptanceTool.선호도_등록(obId, 4.0, PIKA);
        memberAcceptanceTool.선호도_등록(stellaId, 3.0, PIKA);

        //given
        HttpResponse httpResponse = request()
                .get("/drinks?category=" + categoryKey + "&page=" + page + "&sortBy=" + sortBy)
                .withDocument("drinks/show/allSortByPreferenceAndCategoryByBEER")
                .build();
        List<DrinkResponse> drinkResponses = httpResponse.convertBodyToList(DrinkResponse.class);
        //when
        //then
        페이징_검증(httpResponse.pageInfo(), 1, 1, 10, 8);
        assertThat(drinkResponses.get(0).getCategory().getKey()).isEqualTo(categoryKey);
        assertThat(drinkResponses.get(0).getId()).isEqualTo(obId);
        assertThat(drinkResponses.get(1).getId()).isEqualTo(stellaId);
    }

    @DisplayName("메인 페이지 - 올바르지 않은 정렬 기준")
    @Test
    void showDrinkByCategoryFailSortBy() {

        final String categoryKey = "BEER";
        final int page = 1;
        final String sortBy = "strange";

        //given
        JujeolExceptionDto errorResponse = request()
                .get("/drinks?category=" + categoryKey + "&page=" + page + "&sortBy=" + sortBy)
                .withDocument("drinks/show/drinksByCategory-fail-InvalidSort")
                .build()
                .errorResponse();
        //when
        //then

        예외_검증(errorResponse, INVALID_SORT_BY);
    }

    @DisplayName("상세페이지 조회 - 성공")
    @Test
    public void showDrinkDetailTest() {
        //given
        final Long obId = 주류_아이디(OB);
        //when
        DrinkResponse drinkResponse = request()
                .get("/drinks/{id}", obId)
                .withDocument("drinks/show/detail")
                .withUser()
                .build()
                .convertBody(DrinkResponse.class);

        //then
        assertThat(drinkResponse.getName()).isEqualTo(OB.getName());
    }

    @DisplayName("상세페이지 조회 - 실패 (찾을 수 없는 id)")
    @Test
    public void showDrinkDetailTest_fail() {
        //when
        final JujeolExceptionDto errorResponse = request()
                .get("/drinks/{id}", Long.MAX_VALUE)
                .withDocument("drinks/show/detail-fail")
                .build().errorResponse();

        //then
        예외_검증(errorResponse, NOT_FOUND_DRINK);
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

    private Long 주류_아이디(DrinkTestContainer drinkTestContainer) {
        return drinkAcceptanceTool.주류_아이디_조회(drinkTestContainer.getName());
    }
}
