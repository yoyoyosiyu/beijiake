package com.beijiake.web.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Setter // need setter method to works
@Getter
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "authorization-server")
@Component
public class AuthorizationServerProperties {

    String tokenUrl;
    String clientId;
    String clientSecret;

}
