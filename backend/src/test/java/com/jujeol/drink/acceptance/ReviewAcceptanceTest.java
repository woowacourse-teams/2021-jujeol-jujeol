package com.jujeol.drink.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.jujeol.AcceptanceTest;
import com.jujeol.commons.exception.ExceptionCodeAndDetails;
import com.jujeol.commons.exception.JujeolExceptionDto;
import com.jujeol.drink.application.dto.ReviewRequest;
import com.jujeol.drink.application.dto.ReviewResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class ReviewAcceptanceTest extends AcceptanceTest {

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

    @DisplayName("리뷰 수정 - 실패(리뷰와 주류 불일치)")
    @Test
    public void updateReviewTest_fail_notExistReviewInDrink() {
        //given
        String content = "천재 윤지우";
        ReviewRequest reviewRequest = new ReviewRequest(content);

        //when
        ExtractableResponse<Response> response = request()
                .put("/drinks/2/reviews/1", reviewRequest)
                .withDocument("reviews/update-fail-match")
                .build().totalResponse();

        JujeolExceptionDto body = response.body().as(JujeolExceptionDto.class);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(body.getCode()).isEqualTo(ExceptionCodeAndDetails.NOT_EXIST_REVIEW_IN_DRINK.getCode());
        assertThat(body.getMessage())
                .isEqualTo(ExceptionCodeAndDetails.NOT_EXIST_REVIEW_IN_DRINK.getMessage());
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

    @DisplayName("리뷰 삭제 - 실패(리뷰와 주류 불일치)")
    @Test
    public void deleteReviewTest_fail_notExistReviewInDrink() {
        //given
        //when
        ExtractableResponse<Response> response = request()
                .delete("/drinks/2/reviews/1")
                .withDocument("reviews/delete-fail-match")
                .build().totalResponse();

        JujeolExceptionDto body = response.body().as(JujeolExceptionDto.class);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(body.getCode()).isEqualTo(ExceptionCodeAndDetails.NOT_EXIST_REVIEW_IN_DRINK.getCode());
        assertThat(body.getMessage())
                .isEqualTo(ExceptionCodeAndDetails.NOT_EXIST_REVIEW_IN_DRINK.getMessage());
    }
}
