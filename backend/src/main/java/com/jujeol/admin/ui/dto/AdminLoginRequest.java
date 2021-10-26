package com.jujeol.admin.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class AdminLoginRequest {

    private String id;
    private String password;
}
