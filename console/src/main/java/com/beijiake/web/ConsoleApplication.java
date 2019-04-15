package com.beijiake.web;

import com.beijiake.web.config.AuthorizationServerProperties;
import com.beijiake.web.config.StorageProperties;
import com.beijiake.web.service.StorageException;
import com.beijiake.web.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.*;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@SpringBootApplication
@EntityScan(basePackages = {"com.beijiake.data.domain", "com.beijiake.web.domain"})
@EnableJpaRepositories(basePackages = {"com.beijiake.repository", "com.beijiake.web.views"})
@EnableJpaAuditing
public class ConsoleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsoleApplication.class, args);
    }



    @Bean
    CommandLineRunner init(StorageProperties storageProperties, StorageService storageService, RestTemplate restTemplate) {
        return (args)->{

            Logger logger = LoggerFactory.getLogger(getClass());

            try {
                ResponseEntity<String> result = restTemplate.getForEntity("http://localhost:9001/api/categories", String.class);
            }
            catch(HttpStatusCodeException e) {
                logger.error(e.getResponseBodyAsString());
            }
            catch(RestClientException restClientException)
            {
                logger.error(restClientException.getMessage());
            }


            storageService.init();

            String rootLocation = storageProperties.getLocation();



            if (!Files.isWritable(Paths.get(rootLocation)))
            {
                throw new StorageException(String.format("The storage location %s is not writable.", rootLocation));
            }



            logger.info(String.format("The upload storage have already set to location: %s", rootLocation));
        };
    }

}
