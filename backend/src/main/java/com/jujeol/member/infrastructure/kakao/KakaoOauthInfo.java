package com.jujeol.member.infrastructure.kakao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@NoArgsConstructor
public class KakaoOauthInfo {

    @Value("${kakao.client.id:123}")
    private String clientId;
    @Value("${kakao.client.secret:123}")
    private String clientSecret;
    @Value("${kakao.url.access-token:/default}")
    private String kakaoTokenUrl;
    @Value("${kakao.url.profile:/default}")
    private String kakaoUserUrl;
    @Value("${kakao.client.redirect-url:/default}")
    private String kakaoRedirectUrl;
    @Value("${kakao.url.unlink:/default}")
    private String kakaoUnlinkUrl;
}
