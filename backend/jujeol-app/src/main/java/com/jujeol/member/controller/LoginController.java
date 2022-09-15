package com.jujeol.member.controller;

import com.jujeol.commons.CommonResponse;
import com.jujeol.member.controller.request.LoginRequest;
import com.jujeol.member.controller.response.LoginResponse;
import com.jujeol.member.presenter.LoginPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginPresenter loginPresenter;

    @PostMapping("/login/token")
    public ResponseEntity<CommonResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(CommonResponse.from(loginPresenter.login(loginRequest)));
    }
}
