package com.jujeol.member.controller;

import com.jujeol.member.presenter.LoginPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final LoginPresenter loginPresenter;


}
