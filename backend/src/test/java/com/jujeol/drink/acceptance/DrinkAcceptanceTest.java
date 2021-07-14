package com.jujeol.drink.acceptance;

import static com.jujeol.TestDataLoader.BEERS;
import static org.assertj.core.api.Assertions.assertThat;

import com.jujeol.AcceptanceTest;
import com.jujeol.commons.exception.ExceptionCodeAndDetails;
import com.jujeol.commons.exception.JujeolExceptionDto;
import com.jujeol.drink.application.dto.DrinkDetailResponse;
import com.jujeol.drink.application.dto.DrinkSimpleResponse;
import com.jujeol.drink.application.dto.ReviewRequest;
import com.jujeol.drink.application.dto.ReviewResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class DrinkAcceptanceTest extends AcceptanceTest {

    @DisplayName("전체 조회 - 성공")
    @Test
    public void showDrinksTest() {
        //given
        //when
        List<DrinkSimpleResponse> drinkSimpleResponse = request()
                .get("/drinks")
                .withDocument("drinks/show/all")
                .build().convertBodyToList(DrinkSimpleResponse.class);

        //then
        List<DrinkSimpleResponse> expectedResult = BEERS.stream()
                .filter(drink -> drink.getId() < 8)
                .map(drink -> DrinkSimpleResponse.from(drink, ""))
                .collect(Collectors.toList());

        assertThat(expectedResult).containsAll(drinkSimpleResponse);
    }

    @DisplayName("단일 조회 - 성공")
    @Test
    public void showDrinkDetailTest() {
        //given
        //when
        DrinkDetailResponse drinkDetailResponse = request()
                .get("/drinks/1")
                .withDocument("drinks/show/detail")
                .build().convertBody(DrinkDetailResponse.class);

        //then
        DrinkDetailResponse expectedResult = DrinkDetailResponse.from(BEERS.get(0), "");

        assertThat(expectedResult).isEqualTo(drinkDetailResponse);
    }

    @DisplayName("단일 조회 - 실패 (찾을 수 없는 id)")
    @Test
    public void showDrinkDetailTest_fail() {
        //given
        //when
        ExtractableResponse<Response> response = request()
                .get("drinks/1000")
                .withDocument("drinks/show/detail-fail")
                .build().totalResponse();

        JujeolExceptionDto body = response.body().as(JujeolExceptionDto.class);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(body.getCode()).isEqualTo(ExceptionCodeAndDetails.NOT_FOUND_DRINK.getCode());
        assertThat(body.getMessage())
                .isEqualTo(ExceptionCodeAndDetails.NOT_FOUND_DRINK.getMessage());
    }

    @DisplayName("리뷰 생성 - 성공")
    @Test
    void createReviewTest() {
        //given
        String content = "너무 맛있어요 - 소롱";
        ReviewRequest reviewRequest = new ReviewRequest(content);
        ExtractableResponse<Response> response = request()
                .post("/drinks/1/reviews", reviewRequest)
                .withDocument("reviews/create")
                .build().totalResponse();
        //when
        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("리뷰 생성 - 실패(존재하지 않는 주류 id)")
    @Test
    void createReviewTest_fail() {
        //given
        String content = "너무 맛있어요 - 피카";
        ReviewRequest reviewRequest = new ReviewRequest(content);
        ExtractableResponse<Response> response = request()
                .post("/drinks/" + Integer.MAX_VALUE + "/reviews", reviewRequest)
                .withDocument("reviews/create-fail")
                .build().totalResponse();
        //when
        JujeolExceptionDto body = response.body().as(JujeolExceptionDto.class);
        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(body.getCode()).isEqualTo(ExceptionCodeAndDetails.NOT_FOUND_DRINK.getCode());
        assertThat(body.getMessage())
                .isEqualTo(ExceptionCodeAndDetails.NOT_FOUND_DRINK.getMessage());
    }

    @DisplayName("리뷰 삭제 - 성공")
    @Test
    public void deleteReviewTest() {
        //given
        //when
        ExtractableResponse<Response> response = request()
                .delete("/drinks/1/reviews/1")
                .withDocument("reviews/delete")
                .build().totalResponse();
        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("리뷰 삭제 - 실패(잘못된 Drink id)")
    @Test
    public void deleteReviewTest_fail_notFoundDrink() {
        //given
        //when
        ExtractableResponse<Response> response = request()
                .delete("/drinks/" + Integer.MAX_VALUE + "/reviews/1")
                .withDocument("reviews/delete-fail-drink")
                .build().totalResponse();

        JujeolExceptionDto body = response.body().as(JujeolExceptionDto.class);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(body.getCode()).isEqualTo(ExceptionCodeAndDetails.NOT_FOUND_DRINK.getCode());
        assertThat(body.getMessage())
                .isEqualTo(ExceptionCodeAndDetails.NOT_FOUND_DRINK.getMessage());
    }

    @DisplayName("리뷰 삭제 - 실패(잘못된 Review id)")
    @Test
    public void deleteReviewTest_fail_notFoundReview() {
        //given
        //when
        ExtractableResponse<Response> response = request()
                .delete("/drinks/1/reviews/-1")
                .withDocument("reviews/delete-fail-review")
                .build().totalResponse();

        JujeolExceptionDto body = response.body().as(JujeolExceptionDto.class);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(body.getCode()).isEqualTo(ExceptionCodeAndDetails.NOT_FOUND_REVIEW.getCode());
        assertThat(body.getMessage())
                .isEqualTo(ExceptionCodeAndDetails.NOT_FOUND_REVIEW.getMessage());
    }

    @DisplayName("리뷰 수정 - 성공")
    @Test
    public void updateReview() {
        //given
        String content = "천재 윤지우";
        ReviewRequest reviewRequest = new ReviewRequest(content);

        //when
        ExtractableResponse<Response> response = request()
                .put("/drinks/1/reviews/1", reviewRequest)
                .withDocument("reviews/update")
                .build().totalResponse();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("리뷰 수정 - 실패(잘못된 Drink id)")
    @Test
    public void updateReview_fail_notFoundDrink() {
        //given
        String content = "천재 윤지우";
        ReviewRequest reviewRequest = new ReviewRequest(content);

        //when
        ExtractableResponse<Response> response = request()
                .put("/drinks/" + Integer.MAX_VALUE + "/reviews/1", reviewRequest)
                .withDocument("reviews/update-fail-drink")
                .build().totalResponse();

        JujeolExceptionDto jujeolExceptionDto = response.body().as(JujeolExceptionDto.class);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(jujeolExceptionDto.getCode()).isEqualTo(ExceptionCodeAndDetails.NOT_FOUND_DRINK.getCode());
        assertThat(jujeolExceptionDto.getMessage())
                .isEqualTo(ExceptionCodeAndDetails.NOT_FOUND_DRINK.getMessage());
    }

    @DisplayName("리뷰 수정 - 실패(잘못된 Review id)")
    @Test
    public void updateReview_fail_notFoundReview() {
        //given
        String content = "천재 윤지우";
        ReviewRequest reviewRequest = new ReviewRequest(content);

        //when
        ExtractableResponse<Response> response = request()
                .put("/drinks/1/reviews/" + Integer.MAX_VALUE, reviewRequest)
                .withDocument("reviews/update-fail-review")
                .build().totalResponse();

        JujeolExceptionDto jujeolExceptionDto = response.body().as(JujeolExceptionDto.class);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(jujeolExceptionDto.getCode()).isEqualTo(ExceptionCodeAndDetails.NOT_FOUND_REVIEW.getCode());
        assertThat(jujeolExceptionDto.getMessage())
                .isEqualTo(ExceptionCodeAndDetails.NOT_FOUND_REVIEW.getMessage());
    }

    @DisplayName("리뷰 조회 - 성공")
    @Test
    void showReviews() {
        //given

        //when
        List<ReviewResponse> responsesPageOne = request()
                .get("/drinks/1/reviews?page=0")
                .withDocument("reviews/show")
                .build().convertBodyToList(ReviewResponse.class);

        List<ReviewResponse> responsesPageTwo = request()
                .get("/drinks/1/reviews?page=1")
                .build().convertBodyToList(ReviewResponse.class);

        //then
        assertThat(responsesPageOne).hasSize(10);
        assertThat(responsesPageTwo).hasSize(2);
    }
}
