package com.jujeol.review.acceptance;

import com.jujeol.RequestBuilder;
import com.jujeol.member.member.application.dto.PreferenceDto;
import com.jujeol.review.application.dto.ReviewWithAuthorDto;
import com.jujeol.review.exception.NotExistReviewInDrinkException;
import com.jujeol.review.ui.dto.ReviewCreateRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

@Component
@ActiveProfiles("test")
public class ReviewAcceptanceTool {

    @Autowired
    private RequestBuilder requestBuilder;

    public List<ReviewWithAuthorDto> 리뷰_조회(Long drinkId) {
        return requestBuilder.builder()
                .get("/reviews?drink=" + drinkId)
                .withoutLog()
                .build()
                .convertBodyToList(ReviewWithAuthorDto.class);
    }

    public void 리뷰_등록(String token, String content, Long drinkId) {
        requestBuilder.builder()
                .put("/members/me/drinks/" + drinkId + "/preference", PreferenceDto.create(5.0))
                .withoutLog()
                .withUser(token)
                .build().totalResponse();

        requestBuilder.builder()
                .post("/reviews", new ReviewCreateRequest(content, drinkId))
                .withoutLog()
                .withUser(token)
                .build().totalResponse();
    }

    public ReviewWithAuthorDto 리뷰_조회(Long drinkId, String content) {
        return requestBuilder.builder()
                .get("/reviews?drink=" + drinkId)
                .withoutLog()
                .build()
                .convertBodyToList(ReviewWithAuthorDto.class)
                .stream().filter(reviewResponse -> reviewResponse.getContent().equals(content))
                .findAny().orElseThrow(NotExistReviewInDrinkException::new);
    }
}
