package com.beijiake.resourceservertest.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/do")
    public String doGet() {

        return "administrator works,hh!";
    }
}
