package com.beijiake.authorserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@EnableAuthorizationServer
@SpringBootApplication
public class AuthorServerApplication {

    public static void main(String[] args) {

        SpringApplication.run(AuthorServerApplication.class, args);
    }

}
