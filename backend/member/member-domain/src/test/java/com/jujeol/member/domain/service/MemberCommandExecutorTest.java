package com.jujeol.member.domain.service;

import com.jujeol.member.DomainTestContext;
import com.jujeol.member.domain.exception.DuplicatedMemberException;
import com.jujeol.member.domain.exception.DuplicatedNicknameException;
import com.jujeol.member.domain.exception.NotExistMemberException;
import com.jujeol.member.domain.model.Biography;
import com.jujeol.member.domain.model.Nickname;
import com.jujeol.member.domain.model.Provider;
import com.jujeol.member.domain.model.ProviderName;
import com.jujeol.member.domain.usecase.MemberRegisterUseCase;
import com.jujeol.member.domain.usecase.MemberUpdateUseCase;
import com.jujeol.member.domain.usecase.command.MemberRegisterCommand;
import com.jujeol.member.domain.usecase.command.MemberUpdateCommand;
import com.jujeol.member.rds.entity.MemberEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
            MemberEntity member = memberRepository.findAll()
                .stream()
                .findAny()
                .orElseThrow();

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

    @Nested
    @DisplayName("회원 수정")
    class MemberUpdateUseCaseTest {

        private MemberUpdateUseCase sut;

        @BeforeEach
        void setUp() {
            sut = memberCommandExecutor;
        }

        @Test
        void 수정() {
            // given
            ProviderName providerName = ProviderName.KAKAO;
            String provideId = "1234";
            String originNickname = "nabom";
            String originBio = "hello";
            Long memberId = saveMember(providerName, provideId, originNickname, originBio).getId();

            String targetNickname = "bomin";
            String targetBio = "hihi";

            MemberUpdateCommand command =
                MemberUpdateCommand.create(memberId, Nickname.create(targetNickname), Biography.create(targetBio));

            // when
            sut.update(command);

            // then
            MemberEntity member = memberRepository.findById(memberId)
                .stream()
                .findAny()
                .orElseThrow();

            assertThat(member.getId()).isEqualTo(memberId);
            assertThat(member.getProvideId()).isEqualTo(provideId);
            assertThat(member.getProviderName()).isEqualTo(providerName);
            assertThat(member.getBiography()).isEqualTo(targetBio);
            assertThat(member.getNickname()).isEqualTo(targetNickname);
        }

        @Test
        void 회원_존재x() {
            // given
            String targetNickname = "bomin";
            String targetBio = "hihi";

            MemberUpdateCommand command =
                MemberUpdateCommand.create(1L, Nickname.create(targetNickname), Biography.create(targetBio));

            // when, then
            assertThatThrownBy(() -> sut.update(command))
                .isExactlyInstanceOf(NotExistMemberException.class);
        }

        @Test
        void 중복_닉네임() {
            // given
            ProviderName providerName = ProviderName.KAKAO;
            String provideId = "1234";
            String originNickname = "nabom";
            String originBio = "hello";
            Long memberId = saveMember(providerName, provideId, originNickname, originBio).getId();

            String targetNickname = "bomin";
            String targetBio = "hihi";

            saveMember(providerName, "1111", targetNickname, targetBio);

            MemberUpdateCommand command =
                MemberUpdateCommand.create(memberId, Nickname.create(targetNickname), Biography.create(targetBio));

            // when
            assertThatThrownBy(() -> sut.update(command))
                .isExactlyInstanceOf(DuplicatedNicknameException.class);
        }
    }

    private MemberEntity saveMember(ProviderName providerName, String provideId, String originNickname, String originBio) {
        return memberRepository.save(
            MemberEntity.builder()
                .providerName(providerName)
                .provideId(provideId)
                .nickname(originNickname)
                .biography(originBio)
                .build()
        );
    }
}