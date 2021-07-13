package com.jujeol.member.infrastructure;

import com.jujeol.member.application.SocialClient;
import com.jujeol.member.application.SocialLoginStrategyFactory;
import com.jujeol.member.domain.ProviderName;
import com.jujeol.member.infrastructure.kakao.KakaoClient;
import com.jujeol.member.infrastructure.kakao.KakaoOauthInfo;
import java.util.EnumMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Profile({"dev", "prod", "local"})
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
