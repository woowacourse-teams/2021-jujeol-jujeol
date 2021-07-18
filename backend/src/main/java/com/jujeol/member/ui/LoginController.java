package com.jujeol.member.ui;

import com.jujeol.commons.dto.CommonResponse;
import com.jujeol.member.application.LoginService;
import com.jujeol.member.application.dto.TokenRequest;
import com.jujeol.member.application.dto.TokenResponse;
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
    public ResponseEntity<CommonResponse<TokenResponse>> login(
            @RequestBody TokenRequest tokenRequest) {
        return ResponseEntity.ok(CommonResponse.from(loginService.createToken(tokenRequest)));
    }
}
