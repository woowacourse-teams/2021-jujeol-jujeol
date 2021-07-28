package com.jujeol.drink.acceptance;

import com.jujeol.RequestBuilder;
import com.jujeol.drink.application.dto.ReviewRequest;
import com.jujeol.drink.application.dto.ReviewResponse;
import com.jujeol.drink.exception.NotExistReviewInDrinkException;
import com.jujeol.member.fixture.TestMember;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

@Component
@ActiveProfiles("test")
public class ReviewAcceptanceTool {

    @Autowired
    private RequestBuilder requestBuilder;

    public List<ReviewResponse> 리뷰_조회(Long drinkId) {
        return requestBuilder.builder()
                .get("/drinks/{id}/reviews", drinkId)
                .withoutLog()
                .build()
                .convertBodyToList(ReviewResponse.class);
    }

    public void 리뷰_등록(TestMember testMember, String content, Long drinkId) {
        requestBuilder.builder()
                .post("/drinks/{id}/reviews", new ReviewRequest(content), drinkId)
                .withoutLog()
                .withUser(testMember)
                .build().totalResponse();
    }

    public ReviewResponse 리뷰_조회(Long drinkId, String content) {
        return requestBuilder.builder()
                .get("/drinks/{id}/reviews", drinkId)
                .withoutLog()
                .build()
                .convertBodyToList(ReviewResponse.class)
                .stream().filter(reviewResponse -> reviewResponse.getContent().equals(content))
                .findAny().orElseThrow(NotExistReviewInDrinkException::new);
    }
}
