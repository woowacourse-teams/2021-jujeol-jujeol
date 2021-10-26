package com.jujeol.admin.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminViewController {

    @GetMapping("/admin")
    public String adminPage() {
        return "admin/html/index.html";
    }

    @GetMapping("/admin/login")
    public String adminLoginPage() {
        return "admin/html/pages/login.html";
    }
}
