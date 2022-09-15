package com.jujeol.oauth.configuration;

import com.jujeol.oauth.kakao.KakaoOauthInfo;
import com.jujeol.oauth.kakao.KakaoSocialClient;
import com.jujeol.oauth.model.OauthSocialClient;
import com.jujeol.oauth.model.SocialClient;
import com.jujeol.oauth.utils.ClientResponseConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Configuration
public class OauthConfiguration {

    @ConditionalOnMissingBean
    @Bean
    public SocialClient socialClient(RestTemplate restTemplate, ClientResponseConverter clientResponseConverter, KakaoOauthInfo kakaoOauthInfo) {
        return new OauthSocialClient(Arrays.asList(
            new KakaoSocialClient(restTemplate, clientResponseConverter, kakaoOauthInfo)
        ));
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
