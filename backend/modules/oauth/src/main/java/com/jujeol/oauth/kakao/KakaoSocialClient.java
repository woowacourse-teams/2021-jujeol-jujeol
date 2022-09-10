package com.jujeol.oauth.kakao;


import com.jujeol.oauth.exception.OauthAccessException;
import com.jujeol.oauth.kakao.request.KakaoAccessTokenRequest;
import com.jujeol.oauth.model.MemberDetails;
import com.jujeol.oauth.model.OauthSocial;
import com.jujeol.oauth.model.ProviderName;
import com.jujeol.oauth.utils.ClientResponseConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Slf4j
public class KakaoSocialClient implements OauthSocial {

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
    public boolean isAssignable(ProviderName providerName) {
        return ProviderName.KAKAO == providerName;
    }

    @Override
    public String getAccessToken(String socialCode) {

        HttpHeaders headers = new HttpHeaders();
        headers.add(CONTENT_TYPE, DEFAULT_CHARSET);

        final KakaoAccessTokenRequest kakaoAccessTokenRequest = new KakaoAccessTokenRequest(
                GRANT_TYPE, kakaoOauthInfo.getClientId(), kakaoOauthInfo.getKakaoRedirectUrl(),
                socialCode,
                kakaoOauthInfo.getClientSecret());

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    kakaoOauthInfo.getKakaoTokenUrl(),
                    HttpMethod.POST,
                    new HttpEntity<>(
                            clientResponseConverter.convertHttpBody(kakaoAccessTokenRequest),
                            headers),
                    String.class
            );
            return clientResponseConverter.extractDataAsString(response.getBody(), ACCESS_TOKEN);
        } catch (HttpClientErrorException e) {
            log.error(e.getMessage(), e);
            throw new OauthAccessException();
        }
    }

    @Override
    public MemberDetails getMemberDetails(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(AUTHORIZATION, String.format(BEARER_FORM, accessToken));
        try {
            final String body = restTemplate
                .exchange(kakaoOauthInfo.getKakaoUserUrl(), HttpMethod.GET,
                    new HttpEntity<>(httpHeaders),
                    String.class)
                .getBody();
            final String id = clientResponseConverter.extractDataAsString(body, ID);
            return new MemberDetails(id, ProviderName.KAKAO);
        } catch (HttpClientErrorException e) {
            log.error(e.getMessage(), e);
            throw new OauthAccessException();
        }
    }
}
