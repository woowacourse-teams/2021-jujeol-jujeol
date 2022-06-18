package com.jujeol.member.presenter;

import com.jujeol.IntegrationTestContext;
import com.jujeol.commons.exception.NotAuthorizedException;
import com.jujeol.member.controller.request.UpdateMeRequest;
import com.jujeol.member.controller.response.MemberInfoResponse;
import com.jujeol.member.domain.model.ProviderName;
import com.jujeol.member.rds.entity.MemberEntity;
import com.jujeol.member.resolver.LoginMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberPresenterTest extends IntegrationTestContext {

    @Autowired
    private MemberPresenter sut;

    @Nested
    @DisplayName("내 정보 조회")
    class FindMemberInfo {

        @Test
        void 조회() {
            // given
            String nickname = "nabom";
            String biography = "나는 나봄";
            ProviderName providerName = ProviderName.KAKAO;
            String provideId = "1234";
            MemberEntity savedMember = saveMember(providerName, provideId, nickname, biography);

            LoginMember loginMember = new LoginMember(savedMember.getId(), LoginMember.Authority.USER);

            // when
            MemberInfoResponse response = sut.findMemberInfo(loginMember);

            // then
            assertThat(response.getId()).isEqualTo(savedMember.getId());
            assertThat(response.getBio()).isEqualTo(savedMember.getBiography());
            assertThat(response.getNickname()).isEqualTo(savedMember.getNickname());
        }

        @Test
        void 익명_유저_접근() {
            // given
            LoginMember anonymous = LoginMember.anonymous();

            // when, then
            assertThatThrownBy(() -> sut.findMemberInfo(anonymous))
                .isExactlyInstanceOf(NotAuthorizedException.class);
        }

        @Test
        void 존재하지_않는_유저() {
            // given
            LoginMember loginMember = new LoginMember(1L, LoginMember.Authority.USER);

            // when, then
            assertThatThrownBy(() -> sut.findMemberInfo(loginMember))
                .isExactlyInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("내 정보 수정")
    class UpdateMe {

        @Test
        void 내정보_수정() {
            // given
            String nickname = "nabom";
            String biography = "나는 나봄";
            ProviderName providerName = ProviderName.KAKAO;
            String provideId = "1234";
            MemberEntity savedMember = saveMember(providerName, provideId, nickname, biography);

            String targetNickname = "bom";
            String targetBio = "hello";
            Long memberId = savedMember.getId();

            LoginMember loginMember = new LoginMember(memberId, LoginMember.Authority.USER);

            // when
            sut.updateMe(loginMember, new UpdateMeRequest(targetNickname, targetBio));

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
        void 익명_유저_접근() {
            // given
            String nickname = "nabom";
            String biography = "나는 나봄";
            ProviderName providerName = ProviderName.KAKAO;
            String provideId = "1234";
            saveMember(providerName, provideId, nickname, biography);

            String targetNickname = "bom";
            String targetBio = "hello";

            // when, then
            assertThatThrownBy(() -> sut.updateMe(LoginMember.anonymous(), new UpdateMeRequest(targetNickname, targetBio)))
                .isExactlyInstanceOf(NotAuthorizedException.class);
        }
    }

    private MemberEntity saveMember(ProviderName providerName, String provideId, String nickname, String bio) {
        return memberRepository.save(
            MemberEntity.builder()
                .providerName(providerName)
                .provideId(provideId)
                .nickname(nickname)
                .biography(bio)
                .build()
        );
    }
}