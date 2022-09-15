package com.jujeol.member.presenter;

import com.jujeol.member.controller.request.LoginRequest;
import com.jujeol.member.controller.response.LoginResponse;
import com.jujeol.member.domain.model.Token;
import com.jujeol.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginPresenter {

    private final MemberService memberService;

    public LoginResponse login(LoginRequest loginRequest) {
        Token token = memberService.loginOrRegister(loginRequest.getProviderName(), loginRequest.getCode());
        return new LoginResponse(token.getAccessToken());
    }
}
