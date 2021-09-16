package com.jujeol.admin.acceptance;

import com.jujeol.RequestBuilder;
import com.jujeol.RequestBuilder.HttpResponse;
import com.jujeol.admin.ui.dto.AdminDrinkRequest;
import com.jujeol.drink.DrinkTestContainer;
import java.util.List;
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
        final List<AdminDrinkRequest> adminDrinkRequests = DrinkTestContainer
                .asAdminRequestList(drinkTestContainers);
        requestBuilder.builder().post("/admin/drinks", adminDrinkRequests).withoutLog().build();
    }
}
