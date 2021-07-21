package com.jujeol.member.ui;

import com.jujeol.commons.dto.CommonResponse;
import com.jujeol.member.application.LoginService;
import com.jujeol.member.ui.dto.SocialProviderCodeRequest;
import com.jujeol.member.application.dto.TokenDto;
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
