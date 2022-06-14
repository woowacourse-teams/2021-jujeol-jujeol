package com.jujeol.member.presenter;

import com.jujeol.IntegrationTestContext;
import com.jujeol.member.controller.request.LoginRequest;
import com.jujeol.member.controller.response.LoginResponse;
import com.jujeol.member.domain.model.ProviderName;
import com.jujeol.member.rds.entity.MemberEntity;
import com.jujeol.oauth.model.MemberDetails;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class LoginPresenterTest extends IntegrationTestContext {

    @Autowired
    private LoginPresenter sut;

    @Nested
    @DisplayName("어드민 주류 등록")
    class LoginOrRegisterMember {

        @Test
        void 회원가입() {
            // given
            String code = "1234";
            String accountId = "nabom";
            ProviderName providerName = ProviderName.KAKAO;
            String token = "accesstoken";
            String expression = "expression";
            String abbreviation = "abbr";

            com.jujeol.oauth.model.ProviderName authProviderName = com.jujeol.oauth.model.ProviderName.valueOf(providerName.name());
            when(socialClient.getDetails(eq(code), eq(authProviderName)))
                .thenReturn(new MemberDetails(accountId, authProviderName));

            fixedCheersExpressionProvider.fixExpression(expression, abbreviation);
            fixedTokenProvider.fixToken(token);

            LoginRequest loginRequest = LoginRequest.builder()
                .providerName(providerName)
                .code(code)
                .build();

            // when
            LoginResponse response = sut.login(loginRequest);

            // then
            assertThat(response.getAccessToken()).isEqualTo(token);
            MemberEntity savedMember = memberRepository.findByProvideId(accountId).orElseThrow();
            assertThat(savedMember.getId()).isNotNull();
            assertThat(savedMember.getProvideId()).isEqualTo(accountId);
            assertThat(savedMember.getProviderName()).isEqualTo(providerName);
            assertThat(savedMember.getNickname()).isEqualTo(abbreviation);
            assertThat(savedMember.getBiography()).isEqualTo(expression);
        }

        @Test
        void 로그인() {
            // given
            String code = "1234";
            String accountId = "nabom";
            ProviderName providerName = ProviderName.KAKAO;
            String token = "accesstoken";
            String expression = "expression";
            String abbreviation = "abbr";
             memberRepository.save(
                MemberEntity.builder()
                    .provideId(accountId)
                    .providerName(providerName)
                    .nickname(abbreviation)
                    .biography(expression)
                    .build()
             );

            com.jujeol.oauth.model.ProviderName authProviderName = com.jujeol.oauth.model.ProviderName.valueOf(providerName.name());
            when(socialClient.getDetails(eq(code), eq(authProviderName)))
                .thenReturn(new MemberDetails(accountId, authProviderName));

            // 이미 회원가입된 회원은 cheerExpression 을 생성하지 않는다.
            fixedCheersExpressionProvider.fixExpression("newExpression", "newAbbr");
            fixedTokenProvider.fixToken(token);

            LoginRequest loginRequest = LoginRequest.builder()
                .providerName(providerName)
                .code(code)
                .build();

            // when
            LoginResponse response = sut.login(loginRequest);

            // then
            assertThat(response.getAccessToken()).isEqualTo(token);
            MemberEntity savedMember = memberRepository.findByProvideId(accountId).orElseThrow();
            assertThat(savedMember.getId()).isNotNull();
            assertThat(savedMember.getProvideId()).isEqualTo(accountId);
            assertThat(savedMember.getProviderName()).isEqualTo(providerName);
            assertThat(savedMember.getNickname()).isEqualTo(abbreviation);
            assertThat(savedMember.getBiography()).isEqualTo(expression);
        }
    }
}