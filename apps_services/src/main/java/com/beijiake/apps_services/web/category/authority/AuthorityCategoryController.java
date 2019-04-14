package com.beijiake.apps_services.web.category.authority;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${url-patterns.authority:/authority}/categories")
public class AuthorityCategoryController {

    @RequestMapping
    public String doGet() {
        return "if you see this, it mean you granted authority access privilege!";
    }
}
