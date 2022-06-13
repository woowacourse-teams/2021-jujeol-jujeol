package com.jujeol.member.domain.service;

import com.jujeol.member.DomainTestContext;
import com.jujeol.member.domain.exception.DuplicatedMemberException;
import com.jujeol.member.domain.model.Biography;
import com.jujeol.member.domain.model.Nickname;
import com.jujeol.member.domain.model.Provider;
import com.jujeol.member.domain.model.ProviderName;
import com.jujeol.member.domain.usecase.MemberRegisterUseCase;
import com.jujeol.member.domain.usecase.command.MemberRegisterCommand;
import com.jujeol.member.rds.entity.MemberEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberCommandExecutorTest extends DomainTestContext {

    @Autowired
    private MemberCommandExecutor memberCommandExecutor;

    @Nested
    @DisplayName("회원 등록")
    class MemberRegisterUseCaseTest {

        private MemberRegisterUseCase sut;

        @BeforeEach
        void setUp() {
            sut = memberCommandExecutor;
        }

        @Test
        void 등록() {
            // given
            String provideId = "1234";
            ProviderName providerName = ProviderName.KAKAO;
            Nickname nickname = Nickname.create("nabom");
            Biography biography = Biography.create("biography");
            MemberRegisterCommand command = MemberRegisterCommand.create(Provider.create(provideId, providerName), nickname, biography);

            // when
            sut.register(command);

            // then
            Optional<MemberEntity> optionalMember = memberRepository.findAll()
                .stream()
                .findAny();

            assertThat(optionalMember).isPresent();
            MemberEntity member = optionalMember.get();
            assertThat(member.getId()).isNotNull();
            assertThat(member.getProvideId()).isEqualTo(provideId);
            assertThat(member.getProviderName()).isEqualTo(providerName);
            assertThat(member.getBiography()).isEqualTo(biography.value());
            assertThat(member.getNickname()).isEqualTo(nickname.value());
        }

        @Test
        void 중복_회원() {
            // given
            String provideId = "1234";
            ProviderName providerName = ProviderName.KAKAO;
            Nickname nickname = Nickname.create("nabom");
            Biography biography = Biography.create("biography");
            MemberRegisterCommand command = MemberRegisterCommand.create(Provider.create(provideId, providerName), nickname, biography);

            memberRepository.save(MemberEntity.builder()
                    .providerName(providerName)
                    .provideId(provideId)
                    .biography(biography.value())
                    .nickname(nickname.value())
                .build());

            // when
            assertThatThrownBy(() -> sut.register(command))
                .isInstanceOf(DuplicatedMemberException.class);
        }
    }
}