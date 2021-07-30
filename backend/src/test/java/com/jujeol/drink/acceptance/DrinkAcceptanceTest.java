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
import static com.jujeol.member.fixture.TestMember.NABOM;
import static com.jujeol.member.fixture.TestMember.PIKA;
import static com.jujeol.member.fixture.TestMember.SOLONG;
import static com.jujeol.member.fixture.TestMember.SUNNY;
import static com.jujeol.member.fixture.TestMember.TIKE;
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
import java.util.Arrays;
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

    @DisplayName("전체 조회 - 성공")
    @Test
    public void showDrinksTest() {
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

    @DisplayName("검색 조회 - 성공")
    @Test
    public void showDrinksBySearchTest() {
        //given
        String search = "OB";
        String category = "BEER";
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

    @DisplayName("검색 조회(검색어만 존재) - 성공")
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

    @DisplayName("추천 조회(커스텀) - 성공")
    @Test
    public void showDrinksByCustomConditionTest() {
        //given
        String theme = "best";
        memberAcceptanceTool.선호도_등록(주류_아이디(ESTP), 4.5, WEDGE);
        memberAcceptanceTool.선호도_등록(주류_아이디(ESTP), 5.0, SUNNY);
        memberAcceptanceTool.선호도_등록(주류_아이디(ESTP), 4.6, TIKE);
        memberAcceptanceTool.선호도_등록(주류_아이디(KGB), 3.5, CROFFLE);
        memberAcceptanceTool.선호도_등록(주류_아이디(TIGER_LEMON), 3.3, NABOM);
        memberAcceptanceTool.선호도_등록(주류_아이디(TSINGTAO), 2.5, PIKA);
        memberAcceptanceTool.선호도_등록(주류_아이디(STELLA), 2.3, SOLONG);

        drinkAcceptanceTool.단일_상품_조회(주류_아이디(ESTP));

        drinkAcceptanceTool.단일_상품_조회(주류_아이디(KGB));
        drinkAcceptanceTool.단일_상품_조회(주류_아이디(KGB));
        drinkAcceptanceTool.단일_상품_조회(주류_아이디(KGB));
        drinkAcceptanceTool.단일_상품_조회(주류_아이디(KGB));
        drinkAcceptanceTool.단일_상품_조회(주류_아이디(KGB));
        drinkAcceptanceTool.단일_상품_조회(주류_아이디(KGB));

        drinkAcceptanceTool.단일_상품_조회(주류_아이디(TIGER_LEMON));
        drinkAcceptanceTool.단일_상품_조회(주류_아이디(TIGER_LEMON));


        //when
        List<DrinkSimpleResponse> drinkSimpleResponses = request()
                .get("/drinks/recommendation?theme=" + theme + "&page=1")
                .withDocument("drinks/show/all-theme")
                .build().convertBodyToList(DrinkSimpleResponse.class);

        //then
        final List<String> drinksByCustom =
                Arrays.asList(ESTP.getName(), KGB.getName(), TIGER_LEMON.getName());

        assertThat(drinkSimpleResponses).extracting("name").containsExactlyElementsOf(drinksByCustom);
    }

    @DisplayName("추천 조회(선호도) - 성공")
    @Test
    public void showDrinksByPreferenceTest() {
        //given
        String theme = "preference";
        memberAcceptanceTool.선호도_등록(주류_아이디(ESTP), 4.5, WEDGE);
        memberAcceptanceTool.선호도_등록(주류_아이디(ESTP), 5.0, SUNNY);
        memberAcceptanceTool.선호도_등록(주류_아이디(ESTP), 4.6, TIKE);
        memberAcceptanceTool.선호도_등록(주류_아이디(KGB), 3.5, CROFFLE);
        memberAcceptanceTool.선호도_등록(주류_아이디(TIGER_LEMON), 3.3, NABOM);
        memberAcceptanceTool.선호도_등록(주류_아이디(TSINGTAO), 2.5, PIKA);
        memberAcceptanceTool.선호도_등록(주류_아이디(STELLA), 2.3, SOLONG);

        //when
        List<DrinkSimpleResponse> drinkSimpleResponses = request()
                .get("/drinks/recommendation?theme=" + theme + "&page=1")
                .withDocument("drinks/show/all-theme")
                .build().convertBodyToList(DrinkSimpleResponse.class);

        //then
        final List<String> drinksByPreferences =
                Arrays.asList(ESTP.getName(), KGB.getName(), TIGER_LEMON.getName(), TSINGTAO.getName(), STELLA.getName());

        assertThat(drinkSimpleResponses).extracting("name").containsExactlyElementsOf(drinksByPreferences);
    }

    @DisplayName("추천 조회(조회수) - 성공")
    @Test
    public void showDrinksByViewCountTest() {
        //given
        String theme = "view-count";
        final Long obId = 주류_아이디(OB);
        final Long stellaId = 주류_아이디(STELLA);
        drinkAcceptanceTool.단일_상품_조회(obId);
        drinkAcceptanceTool.단일_상품_조회(obId);
        drinkAcceptanceTool.단일_상품_조회(stellaId);

        //when
        List<DrinkSimpleResponse> drinkSimpleResponses = request()
                .get("/drinks/recommendation?theme=" + theme + "&page=1")
                .build().convertBodyToList(DrinkSimpleResponse.class);

        //then
        assertThat(drinkSimpleResponses).extracting("name")
                .containsExactly(OB.getName(), STELLA.getName());
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
