package com.jujeol.oauth.kakao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@NoArgsConstructor
// TODO : @ConfigurationProperties 를 사용하는 코드로 변경 필요
public class KakaoOauthInfo {

    @Value("${kakao.client.id:}")
    private String clientId;
    @Value("${kakao.client.secret:}")
    private String clientSecret;
    @Value("${kakao.url.access-token:}")
    private String kakaoTokenUrl;
    @Value("${kakao.url.profile:}")
    private String kakaoUserUrl;
    @Value("${kakao.client.redirect-url:}")
    private String kakaoRedirectUrl;
    @Value("${kakao.url.unlink:}")
    private String kakaoUnlinkUrl;
}
