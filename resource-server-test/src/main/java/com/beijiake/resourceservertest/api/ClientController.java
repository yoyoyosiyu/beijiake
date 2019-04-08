package com.beijiake.resourceservertest.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

    @RequestMapping("/client/register")
    @PreAuthorize("#oauth2.hasScope('register_user')")
    public String doRegisterClient() {
        return "Client Words";
    }
}
