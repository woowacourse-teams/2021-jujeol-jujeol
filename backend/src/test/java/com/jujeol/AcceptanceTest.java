package com.jujeol;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jujeol.RequestBuilder.Function;
import com.jujeol.commons.dto.PageInfo;
import com.jujeol.commons.exception.ExceptionCodeAndDetails;
import com.jujeol.commons.exception.JujeolExceptionDto;
import com.jujeol.member.application.LoginService;
import com.jujeol.member.application.dto.TokenDto;
import com.jujeol.member.fixture.SocialLoginMemberFixture;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(RestDocumentationExtension.class)
@ActiveProfiles("test")
public class AcceptanceTest {

    @LocalServerPort
    private int port;
    private RequestBuilder request;
    @Autowired
    private LoginService loginService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TestDataLoader testDataLoader;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        RestAssured.port = port;
        TokenDto token = loginService.createToken(SocialLoginMemberFixture.DEFAULT.toDto());

        request = new RequestBuilder(restDocumentation, token.getAccessToken(), objectMapper);
    }

    @AfterEach
    void afterEach() {
        testDataLoader.removeAll();
    }

    protected Function request() {
        return request.builder();
    }

    protected Function requestWithOtherUser(String accessToken) {
        return request.changeAccessToken(accessToken).builder();
    }

    protected String 회원가입을_하고(SocialLoginMemberFixture socialLoginMemberFixture) {
        final String accessToken = request()
                .post("login/token", socialLoginMemberFixture.toDto())
                .withDocument("member/login/token")
                .build()
                .convertBody(TokenDto.class)
                .getAccessToken();

        return accessToken;
    }

    protected void 페이징_검증(
            PageInfo pageInfo, int currentPage, int lastPage, int countPerPage, int totalSize) {
        assertThat(pageInfo.getCurrentPage()).isEqualTo(currentPage);
        assertThat(pageInfo.getLastPage()).isEqualTo(lastPage);
        assertThat(pageInfo.getCountPerPage()).isEqualTo(countPerPage);
        assertThat(pageInfo.getTotalSize()).isEqualTo(totalSize);
    }

    protected void 예외_검증(JujeolExceptionDto jujeolExceptionDto,
            ExceptionCodeAndDetails exceptionCodeAndDetails) {
        assertThat(jujeolExceptionDto.getCode()).isEqualTo(exceptionCodeAndDetails.getCode());
        assertThat(jujeolExceptionDto.getMessage()).isEqualTo(exceptionCodeAndDetails.getMessage());
    }
}
