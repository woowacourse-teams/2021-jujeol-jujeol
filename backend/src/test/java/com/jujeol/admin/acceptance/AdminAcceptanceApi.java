package com.jujeol.admin.acceptance;

import com.jujeol.RequestBuilder;
import com.jujeol.RequestBuilder.HttpResponse;
import com.jujeol.admin.ui.dto.AdminDrinkRequest;
import com.jujeol.admin.ui.dto.AdminDrinkResponse;
import com.jujeol.commons.exception.JujeolExceptionDto;
import com.jujeol.drink.DrinkTestContainer;
import com.jujeol.drink.exception.NotFoundDrinkException;
import com.jujeol.drink.ui.dto.DrinkDetailResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

@Component
@ActiveProfiles("test")
public class AdminAcceptanceApi {

    @Autowired
    private RequestBuilder requestBuilder;

    public HttpResponse 어드민_주류_데이터_요청() {
        return requestBuilder.builder().get("/admin/drinks").withoutLog().build();
    }

    public void 어드민_주류_데이터_등록(DrinkTestContainer... drinkTestContainers) {
        final List<AdminDrinkRequest> adminDrinkRequests = DrinkTestContainer.asAdminRequestList(drinkTestContainers);
        requestBuilder.builder().post("/admin/drinks", adminDrinkRequests).withoutLog().build();
    }
}
