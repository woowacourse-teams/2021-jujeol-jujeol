package com.jujeol.admin.service;

import com.jujeol.admin.exception.InvalidAdminLoginException;
import com.jujeol.admin.utils.AdminTokenProvider;
import com.jujeol.member.auth.application.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminLoginService {

    @Value("${admin.id:admin}")
    private String adminId;
    @Value("${admin.password:password}")
    private String adminPassword;

    private final AdminTokenProvider tokenProvider;

    public TokenDto login(String id, String password) {
        if(adminId.equals(id) && adminPassword.equals(password)) {
            return TokenDto.create(tokenProvider.createToken());
        }
        throw new InvalidAdminLoginException();
    }
}
