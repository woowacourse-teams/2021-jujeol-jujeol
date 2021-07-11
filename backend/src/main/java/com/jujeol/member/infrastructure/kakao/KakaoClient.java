package com.jujeol.member.infrastructure.kakao;

import com.jujeol.member.application.MemberDetails;
import com.jujeol.member.application.SocialClient;
import com.jujeol.member.infrastructure.ClientResponseConverter;
import com.jujeol.member.infrastructure.kakao.dto.KakaoAccessTokenRequest;
import lombok.RequiredArgsConstructor;
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
    private final KakaoOauthInfo kakaoOauthInfo;

    @Override
    public String getAccessToken(String code) {

        HttpHeaders headers = new HttpHeaders();
        headers.add(CONTENT_TYPE, DEFAULT_CHARSET);

        final KakaoAccessTokenRequest kakaoAccessTokenRequest = new KakaoAccessTokenRequest(
                GRANT_TYPE, kakaoOauthInfo.getClientId(), kakaoOauthInfo.getKakaoRedirectUrl(), code,
                kakaoOauthInfo.getClientSecret());

        ResponseEntity<String> response = restTemplate.exchange(
                kakaoOauthInfo.getKakaoTokenUrl(),
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
                .exchange(kakaoOauthInfo.getKakaoUserUrl(), HttpMethod.GET, new HttpEntity<>(httpHeaders),
                        String.class)
                .getBody();

        final String id = clientResponseConverter.extractDataAsString(body, ID);
        return new KakaoMemberDetails(id, null);
    }

    @Override
    public void unlink(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(AUTHORIZATION, String.format(BEARER_FORM, accessToken));
        restTemplate.exchange(kakaoOauthInfo.getKakaoUnlinkUrl(), HttpMethod.GET, new HttpEntity<>(httpHeaders), Void.class);
    }
}
