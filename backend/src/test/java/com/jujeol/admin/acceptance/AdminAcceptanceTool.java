package com.jujeol.admin.acceptance;

import static com.jujeol.drink.acceptance.DrinkAcceptanceTool.TEST_MULTIPART;

import com.jujeol.admin.ui.dto.AdminDrinkRequest;
import com.jujeol.drink.DrinkTestContainer;
import com.jujeol.member.fixture.TestMember;
import com.jujeol.testtool.RequestBuilder;
import com.jujeol.testtool.response.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

@Component
@ActiveProfiles("test")
public class AdminAcceptanceTool {

    @Autowired
    private RequestBuilder requestBuilder;

    public HttpResponse 어드민_주류_데이터_요청() {
        return requestBuilder.builder().get("/admin/drinks").withoutLog().build();
    }

    public void 어드민_주류_데이터_등록(DrinkTestContainer... drinkTestContainers) {
        for (DrinkTestContainer drinkTestContainer : drinkTestContainers) {
            final AdminDrinkRequest adminDrinkRequest = drinkTestContainer.getAdminDrinkRequest();
                requestBuilder.builder()
                    .post("/admin/drinks", null).withUser(TestMember.WEDGE)
                    .addMultipart("name", adminDrinkRequest.getName())
                    .addMultipart("englishName", adminDrinkRequest.getEnglishName())
                    .addMultipart("categoryKey", adminDrinkRequest.getCategoryKey())
                    .addMultipart("description", adminDrinkRequest.getDescription())
                    .addMultipart("alcoholByVolume", adminDrinkRequest.getAlcoholByVolume())
                    .addMultipart("image", TEST_MULTIPART)
                    .build();
        }
    }

}
