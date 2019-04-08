package com.beijiake.resourceservertest.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/user/do")
    public String doGet() {
        return "User Works";
    }

}
