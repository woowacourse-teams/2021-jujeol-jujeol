package com.jujeol.member.auth.infrastructure;

import com.jujeol.member.auth.application.SocialClient;
import com.jujeol.member.auth.application.SocialLoginStrategyFactory;
import com.jujeol.member.auth.domain.ProviderName;
import com.jujeol.member.auth.infrastructure.kakao.KakaoClient;
import com.jujeol.member.auth.infrastructure.kakao.KakaoOauthInfo;
import java.util.EnumMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Profile({"dev", "prod", "local", "dummy", "repliDummy"})
@RequiredArgsConstructor
public class SocialClientFactory implements SocialLoginStrategyFactory {

    private final RestTemplate restTemplate;
    private final ClientResponseConverter converter;
    private final KakaoOauthInfo kakaoOauthInfo;

    private Map<ProviderName, SocialClient> socialClients;

    @PostConstruct
    private void initialize() {
        socialClients = new EnumMap<>(ProviderName.class);
        socialClients
                .put(ProviderName.KAKAO, new KakaoClient(restTemplate, converter, kakaoOauthInfo));
    }

    @Override
    public SocialClient selectSocialClient(ProviderName providerName) {
        return socialClients.get(providerName);
    }
}
