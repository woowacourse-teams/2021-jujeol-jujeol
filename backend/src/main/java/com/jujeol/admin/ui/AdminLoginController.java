package com.jujeol.admin.ui;

import com.jujeol.admin.service.AdminLoginService;
import com.jujeol.admin.ui.dto.AdminLoginRequest;
import com.jujeol.commons.dto.CommonResponse;
import com.jujeol.member.auth.application.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminLoginController {

    private final AdminLoginService adminLoginService;

    @PostMapping("/admin/login")
    public ResponseEntity<CommonResponse<TokenDto>> adminLogin(@RequestBody AdminLoginRequest adminLoginRequest) {
        return ResponseEntity.ok(CommonResponse.from(adminLoginService.login(
                adminLoginRequest.getId(), adminLoginRequest.getPassword())));
    }
}
