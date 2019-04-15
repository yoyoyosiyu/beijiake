package com.beijiake.api_server.rest.category;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${url-patterns.authority:/api}/categories")
public class CategoryController {

    @RequestMapping
    public String doGet() {
        return "if you see this, it mean you granted authority access privilege!";
    }
}
