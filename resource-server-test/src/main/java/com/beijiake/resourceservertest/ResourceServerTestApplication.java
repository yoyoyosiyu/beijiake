package com.beijiake.resourceservertest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;


@SpringBootApplication
@EntityScan(basePackages = {"com.beijiake.data.domain"})
public class ResourceServerTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResourceServerTestApplication.class, args);
    }

    @RestController
    private static class UserController {

        @GetMapping("/userinfo")
        public Principal doGet(Principal principal) {
            return principal;
        }

    }
}
