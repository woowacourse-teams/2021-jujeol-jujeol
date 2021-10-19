package com.jujeol;

import static org.assertj.core.api.Assertions.assertThat;

import com.jujeol.commons.dto.PageInfo;
import com.jujeol.commons.exception.ExceptionCodeAndDetails;
import com.jujeol.commons.exception.JujeolExceptionDto;
import com.jujeol.drink.acceptance.CategoryAcceptanceTool;
import com.jujeol.testtool.RequestBuilder;
import com.jujeol.testtool.request.RequestApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
@ActiveProfiles("test")
@Transactional
@AutoConfigureRestDocs
public class AcceptanceTest {


    @Autowired
    private RequestBuilder request;
    @Autowired
    private CategoryAcceptanceTool categoryAcceptanceTool;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        request.setRestDocumentationContextProvider(restDocumentation);
        categoryAcceptanceTool.기본_카테고리_저장();
    }

    protected RequestApi request() {
        return request.builder();
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
