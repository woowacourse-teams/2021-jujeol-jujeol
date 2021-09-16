package com.jujeol.admin.acceptance;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jujeol.RequestBuilder;
import com.jujeol.RequestBuilder.HttpResponse;
import com.jujeol.admin.ui.dto.AdminDrinkRequest;
import com.jujeol.admin.ui.dto.AdminDrinkResponse;
import com.jujeol.aws.service.ImageResizerImpl.ImageSize;
import com.jujeol.aws.service.ImageService;
import com.jujeol.commons.dto.CommonResponse;
import com.jujeol.drink.DrinkTestContainer;
import io.restassured.RestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.MultiPartSpecification;
import java.io.File;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.commons.io.Charsets;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

@Component
@ActiveProfiles("test")
public class AdminAcceptanceTool {
    private static final String TEST_FILE_NAME = "testimage.png";
    private static final File THUMBNAIL_IMAGE = new File(new File("").getAbsolutePath() + "/src/test/resources/static/" + TEST_FILE_NAME);

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ImageService imageService;

    @Autowired
    private RequestBuilder requestBuilder;

    public HttpResponse 어드민_주류_데이터_요청() {
        return requestBuilder.builder().get("/admin/drinks").withoutLog().build();
    }

    public List<AdminDrinkResponse> 어드민_주류_데이터_등록(DrinkTestContainer... drinkTestContainers) {
        HashMap<ImageSize, String> map = new HashMap<>();
        map.put(ImageSize.SMALL, "noze.jpeg");
        map.put(ImageSize.MEDIUM, "honeyj.jpeg");
        map.put(ImageSize.LARGE, "aikii.jpeg");
        when(imageService.insert(any())).thenReturn(new EnumMap<>(map));

        ArrayList<AdminDrinkResponse> adminDrinkResponses = new ArrayList<>();
        for (DrinkTestContainer drinkTestContainer : drinkTestContainers) {
            final AdminDrinkRequest adminDrinkRequest = drinkTestContainer.getAdminDrinkRequest();

            final LinkedHashMap data = (LinkedHashMap)RestAssured
                .given()
                .contentType(String.valueOf(ContentType.MULTIPART_FORM_DATA))
                .when()
                .spec(new RequestSpecBuilder()
                    .addMultiPart("image", new File(THUMBNAIL_IMAGE.toURI()))
                    .addMultiPart(setUtf8Charset(adminDrinkRequest.getName(), "name"))
                    .addMultiPart("englishName", adminDrinkRequest.getEnglishName())
                    .addMultiPart("categoryKey", adminDrinkRequest.getCategoryKey())
                    .addMultiPart(setUtf8Charset(adminDrinkRequest.getDescription(), "description"))
                    .addMultiPart("alcoholByVolume",
                        String.valueOf(adminDrinkRequest.getAlcoholByVolume()))
                    .build())
                .post("/admin/drinks")
                .then()
                .extract()
                .body()
                .as(CommonResponse.class)
                .getData();

            adminDrinkResponses.add(objectMapper.convertValue(data, AdminDrinkResponse.class));
        }
        return adminDrinkResponses;
    }

    private MultiPartSpecification setUtf8Charset(String data, String columnName){
        return new MultiPartSpecBuilder(data)
            .controlName(columnName).charset(Charsets.UTF_8).build();
    }
}
