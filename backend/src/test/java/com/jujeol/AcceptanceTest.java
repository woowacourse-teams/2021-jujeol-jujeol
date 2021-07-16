package com.jujeol;

import com.jujeol.RequestBuilder.Function;
import com.jujeol.member.TestSocialLoginFactory;
import com.jujeol.member.application.LoginService;
import com.jujeol.member.application.MemberDetails;
import com.jujeol.member.application.SocialLoginStrategyFactory;
import com.jujeol.member.application.dto.TokenRequest;
import com.jujeol.member.application.dto.TokenResponse;
import com.jujeol.member.domain.Member;
import com.jujeol.member.domain.ProviderName;
import io.restassured.RestAssured;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ExtendWith(RestDocumentationExtension.class)
@ActiveProfiles("test")
public class AcceptanceTest {

    @LocalServerPort
    private int port;
    private RequestBuilder request;
    @Autowired
    private LoginService loginService;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        RestAssured.port = port;
        TokenResponse token = loginService.createToken(new TokenRequest("1234", ProviderName.TEST));
        request = new RequestBuilder(restDocumentation, token.getAccessToken());
    }

    /**
     * use-example :
     *  request()
     *      .get("/path")                   http method
     *      .withoutLog()                   default : true
     *      .withDocument("identifier")     default : withoutDocument
     *      .build();
     */
    protected Function request() {
        return request.builder();
    }
}
