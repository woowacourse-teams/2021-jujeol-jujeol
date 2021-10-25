package com.jujeol.admin.ui.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AdminLoginRequest {

    private String id;
    private String password;
}
