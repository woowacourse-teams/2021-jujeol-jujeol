package com.jujeol.admin.acceptance;

import static com.jujeol.drink.acceptance.DrinkAcceptanceTool.TEST_MULTIPART;

import com.jujeol.RequestBuilder;
import com.jujeol.RequestBuilder.HttpResponse;
import com.jujeol.admin.ui.dto.AdminDrinkRequest;
import com.jujeol.admin.ui.dto.AdminDrinkResponse;
import com.jujeol.drink.DrinkTestContainer;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.specification.MultiPartSpecification;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.Charsets;
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

    public List<AdminDrinkResponse> 어드민_주류_데이터_등록(DrinkTestContainer... drinkTestContainers) {
        ArrayList<AdminDrinkResponse> adminDrinkResponses = new ArrayList<>();
        for (DrinkTestContainer drinkTestContainer : drinkTestContainers) {
            final AdminDrinkRequest adminDrinkRequest = drinkTestContainer.getAdminDrinkRequest();
            adminDrinkResponses.add(
                requestBuilder.builder()
                    .postWithoutData("/admin/drinks")
                    .addMultipart("name", adminDrinkRequest.getName())
                    .addMultipart("englishName", adminDrinkRequest.getEnglishName())
                    .addMultipart("categoryKey", adminDrinkRequest.getCategoryKey())
                    .addMultipart("description", adminDrinkRequest.getDescription())
                    .addMultipart("alcoholByVolume", adminDrinkRequest.getAlcoholByVolume())
                    .addMultipart("image", TEST_MULTIPART)
                    .build()
                    .convertBody(AdminDrinkResponse.class)
            );
        }
        return adminDrinkResponses;
    }

}
