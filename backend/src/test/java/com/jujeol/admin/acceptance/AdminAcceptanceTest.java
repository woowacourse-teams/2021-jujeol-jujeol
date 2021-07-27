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
import com.jujeol.admin.ui.dto.AdminDrinkRequest;
import com.jujeol.admin.ui.dto.AdminDrinkResponse;
import com.jujeol.commons.exception.JujeolExceptionDto;
import com.jujeol.drink.exception.NotFoundDrinkException;
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
    private TestDataLoader testDataLoader;

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
        final HttpResponse drinkHttpResponse = 어드민_주류_데이터_요청();
        final List<AdminDrinkResponse> adminDrinkResponses =
                drinkHttpResponse.convertBodyToList(AdminDrinkResponse.class);

        assertThat(httpResponse.statusCode()).isEqualTo(HttpStatus.OK);
        assertThat(adminDrinkResponses).extracting("name")
                .containsExactlyInAnyOrder(
                        apple().getName(), tsingtao().getName(), kgb().getName(),
                        estp().getName(), stella().getName()
                );

        페이징_검증(drinkHttpResponse.pageInfo(), 1, 1, 20, adminDrinkRequests.size());
    }

    @Test
    @DisplayName("주류 대량 등록 - 실패(이름이 비어있음)")
    public void batchInsert_fail_emptyName() throws Exception {
        //given
        final List<AdminDrinkRequest> request =
                Collections.singletonList(
                        new AdminDrinkRequest("", "test", 2.0, "test", "BEER")
                );

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
                        new AdminDrinkRequest("test", "test", 2.0, "test", Long.toString(Long.MAX_VALUE)));

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
        어드민_주류_데이터_등록(kgb(), stella());
        final Long stellaId = 주류_아이디_조회(stella().getName());

        final AdminDrinkRequest newStella =
                new AdminDrinkRequest("스텔라2", "stella2", 2.0, "test", "BEER");
        //when
        final HttpResponse httpResponse =
                request().put("/admin/drinks/" + stellaId, newStella).build();

        //then
        assertThat(httpResponse.statusCode()).isEqualTo(HttpStatus.OK);

        final DrinkDetailResponse newStellaResponse = 단일_상품_조회(stellaId);
        assertThat(newStellaResponse.getName()).isEqualTo(newStella.getName());
        assertThat(newStellaResponse.getEnglishName()).isEqualTo(newStella.getEnglishName());
        assertThat(newStellaResponse.getAlcoholByVolume()).isEqualTo(newStella.getAlcoholByVolume());
        assertThat(newStellaResponse.getCategory().getCategoryKey()).isEqualTo(newStella.getCategoryKey());
    }

    @Test
    @DisplayName("상품 삭제 - 성공")
    public void deleteDrink_success() throws Exception{
        //given
        어드민_주류_데이터_등록(kgb(), stella());
        final Long stellaId = 주류_아이디_조회(stella().getName());

        //when
        final HttpResponse httpResponse =
                request().delete("/admin/drinks/" + stellaId).build();

        //then
        assertThat(httpResponse.statusCode()).isEqualTo(HttpStatus.OK);
        final JujeolExceptionDto error = 단일_상품_조회_실패(stellaId);
        예외_검증(error, NOT_FOUND_DRINK);
    }

    private HttpResponse 어드민_주류_데이터_요청() {
        return request().get("/admin/drinks").withoutLog().build();
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

    private Long 주류_아이디_조회(String drinkName) {
        return 어드민_주류_데이터_요청().convertBodyToList(AdminDrinkResponse.class)
                .stream().filter(drink -> drink.getName().equals(drinkName))
                .findAny()
                .orElseThrow(NotFoundDrinkException::new)
                .getId();
    }
}
