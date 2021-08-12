package com.jujeol.member.auth.ui;

import com.jujeol.commons.dto.CommonResponse;
import com.jujeol.member.auth.application.LoginService;
import com.jujeol.member.auth.application.dto.TokenDto;
import com.jujeol.member.auth.ui.dto.SocialProviderCodeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login/token")
    public ResponseEntity<CommonResponse<TokenDto>> login(
            @RequestBody SocialProviderCodeRequest socialProviderCodeRequest) {
        return ResponseEntity.ok(CommonResponse.from(loginService.createToken(
                socialProviderCodeRequest.toDto())));
    }
}
