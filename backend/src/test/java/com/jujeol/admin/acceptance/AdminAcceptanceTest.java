package com.jujeol.admin.acceptance;

import static com.jujeol.commons.exception.ExceptionCodeAndDetails.INVALID_DRINK_NAME;
import static com.jujeol.commons.exception.ExceptionCodeAndDetails.NOT_EXIST_CATEGORY;
import static com.jujeol.commons.exception.ExceptionCodeAndDetails.NOT_FOUND_DRINK;
import static com.jujeol.drink.DrinkTestContainer.APPLE;
import static com.jujeol.drink.DrinkTestContainer.ESTP;
import static com.jujeol.drink.DrinkTestContainer.KGB;
import static com.jujeol.drink.DrinkTestContainer.STELLA;
import static com.jujeol.drink.DrinkTestContainer.TSINGTAO;
import static org.assertj.core.api.Assertions.assertThat;

import com.jujeol.AcceptanceTest;
import com.jujeol.RequestBuilder.HttpResponse;
import com.jujeol.admin.ui.dto.AdminDrinkRequest;
import com.jujeol.admin.ui.dto.AdminDrinkResponse;
import com.jujeol.commons.exception.JujeolExceptionDto;
import com.jujeol.drink.DrinkTestContainer;
import com.jujeol.drink.acceptance.DrinkAcceptanceTool;
import com.jujeol.drink.drink.ui.dto.DrinkResponse;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

class AdminAcceptanceTest extends AcceptanceTest {

    @Autowired
    private AdminAcceptanceTool adminAcceptanceTool;
    @Autowired
    private DrinkAcceptanceTool drinkAcceptanceTool;


    @Test
    @DisplayName("주류 등록 - 성공")
    public void batchInsert_success() {
        // given
        final List<AdminDrinkRequest> adminDrinkRequests =
                DrinkTestContainer.asAdminRequestList(APPLE, TSINGTAO, KGB, ESTP, STELLA);

        // when
        final HttpResponse httpResponse = request()
                .post("/admin/drinks", adminDrinkRequests)
                .build();

        // then
        final HttpResponse drinkHttpResponse = adminAcceptanceTool.어드민_주류_데이터_요청();
        final List<AdminDrinkResponse> adminDrinkResponses =
                drinkHttpResponse.convertBodyToList(AdminDrinkResponse.class);

        assertThat(httpResponse.statusCode()).isEqualTo(HttpStatus.OK);
        assertThat(adminDrinkResponses).extracting("name")
                .containsExactlyInAnyOrder(
                        APPLE.getName(), TSINGTAO.getName(), KGB.getName(),
                        ESTP.getName(), STELLA.getName()
                );

        페이징_검증(drinkHttpResponse.pageInfo(), 1, 1, 20, adminDrinkRequests.size());
    }

    @Test
    @DisplayName("주류 대량 등록 - 실패(이름이 비어있음)")
    public void batchInsert_fail_emptyName() throws Exception {
        //given
        final List<AdminDrinkRequest> request =
                Collections.singletonList(
                        new AdminDrinkRequest("", "test", 2.0, null, "BEER", "비어있는 주류다."
                        ));

        //when
        final HttpResponse httpResponse = request().post("/admin/drinks", request).build();

        //then
        assertThat(httpResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        예외_검증(httpResponse.errorResponse(), INVALID_DRINK_NAME);
    }

    @Test
    @DisplayName("주류 대량 등록 - 실패(잘못된 카테고리)")
    public void batchInsert_fail_wrongCategory() throws Exception {
        //given
        final List<AdminDrinkRequest> request =
                Collections.singletonList(
                        new AdminDrinkRequest("test", "test", 2.0, null,
                                Long.toString(Long.MAX_VALUE), "잘못된 카테고리이다."));

        //when
        final HttpResponse httpResponse = request().post("/admin/drinks", request).build();

        //then
        assertThat(httpResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        예외_검증(httpResponse.errorResponse(), NOT_EXIST_CATEGORY);
    }

    @Test
    @DisplayName("상품 수정 - 성공")
    public void updateDrink_success() throws Exception {
        //given
        adminAcceptanceTool.어드민_주류_데이터_등록(KGB, STELLA);
        final Long stellaId = drinkAcceptanceTool.주류_아이디_조회(STELLA.getName());

        final AdminDrinkRequest newStella =
                new AdminDrinkRequest("스텔라2", "stella2", 2.0, null, "BEER", "상세 설명을 수정 중입니다.");
        //when
        final HttpResponse httpResponse =
                request().put("/admin/drinks/{id}", newStella, stellaId).build();

        //then
        assertThat(httpResponse.statusCode()).isEqualTo(HttpStatus.OK);

        final DrinkResponse newStellaResponse = drinkAcceptanceTool.단일_상품_조회(stellaId);
        assertThat(newStellaResponse.getName()).isEqualTo(newStella.getName());
        assertThat(newStellaResponse.getEnglishName()).isEqualTo(newStella.getEnglishName());
        assertThat(newStellaResponse.getAlcoholByVolume())
                .isEqualTo(newStella.getAlcoholByVolume());
        assertThat(newStellaResponse.getCategory().getKey()).isEqualTo(newStella.getCategoryKey());
    }

    @Test
    @DisplayName("상품 삭제 - 성공")
    public void deleteDrink_success() throws Exception {
        //given
        adminAcceptanceTool.어드민_주류_데이터_등록(KGB, STELLA);
        final Long stellaId = drinkAcceptanceTool.주류_아이디_조회(STELLA.getName());

        //when
        final HttpResponse httpResponse =
                request().delete("/admin/drinks/{id}", stellaId).build();

        //then
        assertThat(httpResponse.statusCode()).isEqualTo(HttpStatus.OK);
        final JujeolExceptionDto error = drinkAcceptanceTool.단일_상품_조회_실패(stellaId);
        예외_검증(error, NOT_FOUND_DRINK);
    }
}
