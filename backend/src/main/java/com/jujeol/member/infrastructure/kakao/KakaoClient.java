package com.jujeol.member.infrastructure.kakao;

import com.jujeol.member.application.MemberDetails;
import com.jujeol.member.application.SocialClient;
import com.jujeol.member.infrastructure.ClientResponseConverter;
import com.jujeol.member.infrastructure.kakao.dto.KakaoAccessTokenRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class KakaoClient implements SocialClient {

    private static final String CONTENT_TYPE = "Content-type";
    private static final String AUTHORIZATION = "Authorization";
    private static final String DEFAULT_CHARSET = "application/x-www-form-urlencoded;charset=utf-8";
    private static final String GRANT_TYPE = "authorization_code";
    private static final String BEARER_FORM = "Bearer %s";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String ID = "id";

    private final RestTemplate restTemplate;
    private final ClientResponseConverter clientResponseConverter;

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

    @Override
    public String getAccessToken(String code) {

        HttpHeaders headers = new HttpHeaders();
        headers.add(CONTENT_TYPE, DEFAULT_CHARSET);

        final KakaoAccessTokenRequest kakaoAccessTokenRequest = new KakaoAccessTokenRequest(
                GRANT_TYPE, clientId, kakaoRedirectUrl, code, clientSecret);

        ResponseEntity<String> response = restTemplate.exchange(
                kakaoTokenUrl,
                HttpMethod.POST,
                new HttpEntity<>(
                        clientResponseConverter.convertHttpBody(kakaoAccessTokenRequest),
                        headers),
                String.class
        );

        return clientResponseConverter.extractDataAsString(response.getBody(), ACCESS_TOKEN);
    }

    @Override
    public MemberDetails getDetails(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(AUTHORIZATION, String.format(BEARER_FORM, accessToken));
        final String body = restTemplate
                .exchange(kakaoUserUrl, HttpMethod.GET, new HttpEntity<>(httpHeaders),
                        String.class)
                .getBody();

        final String id = clientResponseConverter.extractDataAsString(body, ID);
        return new KakaoMemberDetails(id, null);
    }

    @Override
    public void unlink(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(AUTHORIZATION, String.format(BEARER_FORM, accessToken));
        restTemplate.exchange(kakaoUnlinkUrl, HttpMethod.GET, new HttpEntity<>(httpHeaders), Void.class);
    }
}