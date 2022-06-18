package com.jujeol;

import com.jujeol.conf.FixedTokenProvider;
import com.jujeol.drink.presenter.DrinkAdminPresenter;
import com.jujeol.member.presenter.LoginPresenter;
import com.jujeol.member.presenter.MemberPresenter;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
public abstract class DocsTestContext {

    @Autowired
    private WebApplicationContext context;

    protected MockMvc mockMvc;

    @MockBean
    protected DrinkAdminPresenter drinkAdminPresenter;

    @MockBean
    protected LoginPresenter loginPresenter;

    @MockBean
    protected MemberPresenter memberPresenter;

    @Autowired
    protected FixedTokenProvider fixedTokenProvider;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(documentationConfiguration(restDocumentation))
            .alwaysDo(print())
            .build();
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(drinkAdminPresenter);
        Mockito.reset(loginPresenter);
        fixedTokenProvider.reset();
    }

    protected MockMvcRequestSpecification given() {
        return RestAssuredMockMvc.given().mockMvc(mockMvc)
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON);
    }
}
