package com.beijiake.web.config;



import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix="storage")
public class StorageProperties {
    private String location = System.getProperty("user.home") + "/upload";

}
