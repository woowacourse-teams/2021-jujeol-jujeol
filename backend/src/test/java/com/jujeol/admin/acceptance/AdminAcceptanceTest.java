package com.jujeol.admin.acceptance;

import static com.jujeol.admin.AdminDrinkRequestContainer.apple;
import static com.jujeol.admin.AdminDrinkRequestContainer.estp;
import static com.jujeol.admin.AdminDrinkRequestContainer.kgb;
import static com.jujeol.admin.AdminDrinkRequestContainer.stella;
import static com.jujeol.admin.AdminDrinkRequestContainer.tsingtao;
import static com.jujeol.commons.exception.ExceptionCodeAndDetails.INVALID_DRINK_NAME;
import static com.jujeol.commons.exception.ExceptionCodeAndDetails.NOT_EXIST_CATEGORY;
import static com.jujeol.commons.exception.ExceptionCodeAndDetails.NOT_FOUND_DRINK;
import static org.assertj.core.api.Assertions.assertThat;

import com.jujeol.AcceptanceTest;
import com.jujeol.RequestBuilder.HttpResponse;
import com.jujeol.TestDataLoader;
import com.jujeol.admin.ui.dto.AdminCategory;
import com.jujeol.admin.ui.dto.AdminDrinkRequest;
import com.jujeol.admin.ui.dto.AdminDrinkResponse;
import com.jujeol.commons.exception.JujeolExceptionDto;
import com.jujeol.drink.ui.dto.DrinkDetailResponse;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

class AdminAcceptanceTest extends AcceptanceTest {

    @Autowired
    TestDataLoader testDataLoader;

    @BeforeEach
    void setUp() {
        testDataLoader.removeAll();
    }

    @Test
    @DisplayName("주류 대량 등록 - 성공")
    public void batchInsert_success() throws Exception {
        // given
        final List<AdminDrinkRequest> adminDrinkRequests = List
                .of(apple(), tsingtao(), kgb(), estp(), stella());

        // when
        final HttpResponse httpResponse = request()
                .post("/admin/drinks", adminDrinkRequests)
                .build();

        // then
        assertThat(httpResponse.statusCode()).isEqualTo(HttpStatus.OK);

        final List<AdminDrinkResponse> adminDrinkResponses = 어드민_주류_데이터_요청();

        assertThat(adminDrinkResponses).extracting("name")
                .containsExactlyInAnyOrder(
                        apple().getName(), tsingtao().getName(), kgb().getName(),
                        estp().getName(), stella().getName()
                );
    }

    @Test
    @DisplayName("주류 대량 등록 - 실패(이름이 비어있음)")
    public void batchInsert_fail_emptyName() throws Exception {
        //given
        final List<AdminDrinkRequest> request =
                Collections.singletonList(
                        new AdminDrinkRequest("", "test", 2.0, "test",
                                new AdminCategory(1L, "beer"))
                );

        //when
        final HttpResponse httpResponse = request().post("/admin/drinks", request).build();

        //then
        assertThat(httpResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(httpResponse.errorResponse().getCode()).isEqualTo(INVALID_DRINK_NAME.getCode());
        assertThat(httpResponse.errorResponse().getMessage())
                .isEqualTo(INVALID_DRINK_NAME.getMessage());
    }

    @Test
    @DisplayName("주류 대량 등록 - 실패(잘못된 카테고리)")
    public void batchInsert_fail_wrongCategory() throws Exception {
        //given
        final List<AdminDrinkRequest> request =
                Collections.singletonList(
                        new AdminDrinkRequest("test", "test", 2.0, "test",
                                new AdminCategory(1L, "bear"))
                );

        //when
        final HttpResponse httpResponse = request().post("/admin/drinks", request).build();

        //then
        assertThat(httpResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(httpResponse.errorResponse().getCode()).isEqualTo(NOT_EXIST_CATEGORY.getCode());
        assertThat(httpResponse.errorResponse().getMessage())
                .isEqualTo(NOT_EXIST_CATEGORY.getMessage());
    }

    @Test
    @DisplayName("상품 수정 - 성공")
    public void updateDrink_success() throws Exception {
        //given
        어드민_주류_데이터_등록(kgb(), stella());
        final List<AdminDrinkResponse> drinks = 어드민_주류_데이터_요청();
        final AdminDrinkResponse stella =
                drinks.stream().filter(drink -> drink.getName().equals(stella().getName()))
                        .findAny().get();

        final AdminDrinkRequest newStella =
                new AdminDrinkRequest("스텔라2", "stella2", 2.0, "test",
                        new AdminCategory(1L, "BEER"));
        //when
        final HttpResponse httpResponse =
                request().put("/admin/drinks/" + stella.getId(), newStella).build();

        //then
        assertThat(httpResponse.statusCode()).isEqualTo(HttpStatus.OK);

        final DrinkDetailResponse newStellaResponse = 단일_상품_조회(stella.getId());
        assertThat(newStellaResponse.getName()).isEqualTo(newStella.getName());
        assertThat(newStellaResponse.getEnglishName()).isEqualTo(newStella.getEnglishName());
        assertThat(newStellaResponse.getAlcoholByVolume()).isEqualTo(newStella.getAlcoholByVolume());
    }

    @Test
    @DisplayName("상품 삭제 - 성공")
    public void deleteDrink_success() throws Exception{
        //given
        어드민_주류_데이터_등록(kgb(), stella());
        final List<AdminDrinkResponse> drinks = 어드민_주류_데이터_요청();
        final AdminDrinkResponse stella =
                drinks.stream().filter(drink -> drink.getName().equals(stella().getName()))
                        .findAny().get();

        //when
        final HttpResponse httpResponse =
                request().delete("/admin/drinks/" + stella.getId()).build();

        //then
        assertThat(httpResponse.statusCode()).isEqualTo(HttpStatus.OK);

        final JujeolExceptionDto error = 단일_상품_조회_실패(stella.getId());
        assertThat(error.getMessage()).isEqualTo(NOT_FOUND_DRINK.getMessage());
        assertThat(error.getCode()).isEqualTo(NOT_FOUND_DRINK.getCode());
    }

    private List<AdminDrinkResponse> 어드민_주류_데이터_요청() {
        return request().get("/admin/drinks").withoutLog().build()
                .convertBodyToList(AdminDrinkResponse.class);
    }

    private void 어드민_주류_데이터_등록(AdminDrinkRequest... adminDrinkRequest) {
        request().post("/admin/drinks", adminDrinkRequest).withoutLog().build();
    }


    private DrinkDetailResponse 단일_상품_조회(Long id) {
        return request().get("/drinks/" + id).withoutLog().build().convertBody(DrinkDetailResponse.class);
    }

    private JujeolExceptionDto 단일_상품_조회_실패(Long id) {
        return request().get("/drinks/" + id).withoutLog().build().errorResponse();
    }
}
