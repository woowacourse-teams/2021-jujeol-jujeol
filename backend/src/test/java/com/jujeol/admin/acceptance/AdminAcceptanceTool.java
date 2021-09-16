package com.jujeol.admin.acceptance;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.jujeol.RequestBuilder;
import com.jujeol.RequestBuilder.HttpResponse;
import com.jujeol.admin.ui.dto.AdminDrinkRequest;
import com.jujeol.aws.service.ImageResizerImpl.ImageSize;
import com.jujeol.aws.service.ImageService;
import com.jujeol.drink.DrinkTestContainer;
import io.restassured.RestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.DecoderConfig;
import io.restassured.specification.MultiPartSpecification;
import java.io.File;
import java.util.EnumMap;
import java.util.HashMap;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

@Component
@ActiveProfiles("test")
public class AdminAcceptanceTool {

    @MockBean
    private ImageService imageService;

    @Autowired
    private RequestBuilder requestBuilder;

    public HttpResponse 어드민_주류_데이터_요청() {
        return requestBuilder.builder().get("/admin/drinks").withoutLog().build();
    }

    public void 어드민_주류_데이터_등록(DrinkTestContainer... drinkTestContainers) {
        HashMap<ImageSize, String> map = new HashMap<>();
        map.put(ImageSize.SMALL, "noze.jpeg");
        map.put(ImageSize.MEDIUM, "honeyj.jpeg");
        map.put(ImageSize.LARGE, "aikii.jpeg");
        when(imageService.insert(any())).thenReturn(new EnumMap<>(map));

        for (DrinkTestContainer drinkTestContainer : drinkTestContainers) {
            final AdminDrinkRequest adminDrinkRequest = drinkTestContainer.getAdminDrinkRequest();

            MockMultipartFile file = new MockMultipartFile(
                    "file",
                    "hello.txt",
                    MediaType.TEXT_PLAIN_VALUE,
                    "Hello, World!".getBytes()
            );

            RestAssured
                    .given()
                        .contentType("multipart/form-data;charset=UTF-8")
                        .multiPart("image", new File("Chungha.png"))
                        .formParam("name", adminDrinkRequest.getName())
                        .formParam("englishName", adminDrinkRequest.getEnglishName())
                        .formParam("categoryKey", adminDrinkRequest.getCategoryKey())
                        .formParam("description", adminDrinkRequest.getDescription())
                        .formParam("alcoholByVolume", adminDrinkRequest.getAlcoholByVolume())
                    .when()
                        .post("/admin/drinks")
                    .then();
        }
    }

    public void 어드민_주류_데이터_등록(DrinkTestContainer drinkTestContainer) {
        HashMap<ImageSize, String> map = new HashMap<>();
        map.put(ImageSize.SMALL, "noze.jpeg");
        map.put(ImageSize.MEDIUM, "honeyj.jpeg");
        map.put(ImageSize.LARGE, "aikii.jpeg");
        when(imageService.insert(any())).thenReturn(new EnumMap<>(map));

        try {
            final AdminDrinkRequest adminDrinkRequest = drinkTestContainer.getAdminDrinkRequest();
            RestAssured
                    .given()
                    .config(RestAssured.config.decoderConfig(DecoderConfig.decoderConfig().defaultContentCharset("UTF-8")))
                    .header("Content-type", "multipart/form-data;charset=UTF-8")
                    .formParam("name", adminDrinkRequest.getName())
                    .formParam("englishName", adminDrinkRequest.getEnglishName())
                    .formParam("alcoholByVolume", adminDrinkRequest.getAlcoholByVolume())
                    .formParam("categoryKey", adminDrinkRequest.getCategoryKey())
                    .formParam("description", adminDrinkRequest.getDescription())
                    .multiPart(new MultiPartSpecBuilder(adminDrinkRequest.getImage().getBytes())
                            .fileName("filename")
                            // controlName is the name of the
                            // RequestParam associated with the
                            // MultipartFile[] array
                            .controlName("image")
                            .mimeType("image/png")
                            .build())
                    .when()
                    .post("/admin/drinks")
                    .then();
        } catch (Exception e){
            e.printStackTrace();
        }
//        requestBuilder.builder().post("/admin/drinks", adminDrinkRequest.getImage(), adminDrinkRequest).withoutLog().build();
    }

    private MultiPartSpecification getMultiPart() {
        return new MultiPartSpecBuilder(new File("Chungha.png")).
                fileName("Chungha.png").
                controlName("image").
                mimeType("image/png").
                build();
    }

}
