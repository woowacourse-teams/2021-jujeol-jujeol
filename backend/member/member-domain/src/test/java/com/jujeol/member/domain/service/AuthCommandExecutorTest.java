package com.jujeol.member.domain.service;

import com.jujeol.member.DomainTestContext;
import com.jujeol.member.domain.exception.NotFoundAuthException;
import com.jujeol.member.domain.model.*;
import com.jujeol.member.domain.usecase.AuthLoginUseCase;
import com.jujeol.member.domain.usecase.command.AuthLoginCommand;
import com.jujeol.member.rds.entity.MemberEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AuthCommandExecutorTest extends DomainTestContext {

    @Autowired
    private AuthCommandExecutor authCommandExecutor;

    @Nested
    @DisplayName("로그인")
    class AuthLoginUseCaseTest {

        private AuthLoginUseCase sut;

        @BeforeEach
        void setUp() {
            sut = authCommandExecutor;
        }

        @Test
        void 로그인() {
            // given
            String provideId = "1234";
            ProviderName providerName = ProviderName.KAKAO;
            Nickname nickname = Nickname.create("nabom");
            Biography biography = Biography.create("biography");
            String token = "accessToken";
            tokenProvider.fixToken(token);

            memberRepository.save(MemberEntity.builder()
                .providerName(providerName)
                .provideId(provideId)
                .biography(biography.value())
                .nickname(nickname.value())
                .build());

            AuthLoginCommand command = AuthLoginCommand.create(Provider.create(provideId, providerName));

            // when
            Token response = sut.login(command);

            // then
            assertThat(response.getAccessToken()).isEqualTo(token);
        }

        @Test
        void 등록되지_않은_계정() {
            // given
            String provideId = "1234";
            ProviderName providerName = ProviderName.KAKAO;
            String token = "accessToken";
            tokenProvider.fixToken(token);

            AuthLoginCommand command = AuthLoginCommand.create(Provider.create(provideId, providerName));

            // when, then
            assertThatThrownBy(() -> sut.login(command))
                .isInstanceOf(NotFoundAuthException.class);
        }
    }
}