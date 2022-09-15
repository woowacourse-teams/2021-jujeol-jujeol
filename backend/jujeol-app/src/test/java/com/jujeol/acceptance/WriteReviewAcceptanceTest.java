package com.jujeol.acceptance;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@ActiveProfiles("local")
public class WriteReviewAcceptanceTest {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .alwaysDo(print())
            .build();
    }

    private MockMvcRequestSpecification given() {
        return RestAssuredMockMvc.given().mockMvc(mockMvc)
            .accept(ContentType.JSON.withCharset(StandardCharsets.UTF_8))
            .contentType(ContentType.JSON.withCharset(StandardCharsets.UTF_8));
    }


}
