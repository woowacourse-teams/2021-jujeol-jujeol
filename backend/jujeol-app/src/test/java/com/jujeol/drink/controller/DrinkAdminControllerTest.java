package com.jujeol.drink.controller;

import com.jujeol.DocsTestContext;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

class DrinkAdminControllerTest extends DocsTestContext {

    @Test
    void 어드민_주류_등록() {
        // given
        String name = "애플";
        String englishName = "Apple";
        double alcoholByVolume = 8.2;
        String categoryKey = "BEER";
        String description = "사과맛이 나는 맥주이다.";

        // when
        MockMvcResponse response = given()
            .contentType(MediaType.MULTIPART_FORM_DATA_VALUE + "; charset=utf-8")
            .formParam("name", name)
            .formParam("englishName", englishName)
            .formParam("categoryKey", categoryKey)
            .formParam("description", description)
            .formParam("alcoholByVolume", alcoholByVolume)
            .multiPart("image", "test image contents", "image/png")
            .when()
            .post("/admin/drinks");

        // then
        response.prettyPrint();

        response.then()
            .apply(document("admin/drink-insert"))
            .statusCode(HttpStatus.OK.value());
    }
}