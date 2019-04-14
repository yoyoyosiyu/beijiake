package com.beijiake.web.service;

import com.beijiake.web.config.AuthorizationServerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

@Service
public class TokenService {

    String accessToken = "";

    @Autowired
    AuthorizationServerProperties authorizationServerProperties;


    @Bean
    RestTemplate remoteServiceTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new ClientHttpRequestInterceptor() {
            @Override
            public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {

                Logger logger = LoggerFactory.getLogger(this.getClass());

                String accessToken = getAccessToken();

                logger.info(String.format("RestTemplate Interceptor user access token: %s to visit %s", accessToken, httpRequest.getURI().toASCIIString()));

                httpRequest.getHeaders().add("Authorization", "Bearer " + accessToken);
                return clientHttpRequestExecution.execute(httpRequest, bytes);
            }
        });

        return restTemplate;
    }


    public String getAccessToken() {
        Logger logger = LoggerFactory.getLogger((this.getClass()));

        if (!this.accessToken.isEmpty()) {
            return this.accessToken;
        }

        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder.build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);

        restTemplate.getInterceptors()
                .add(new BasicAuthenticationInterceptor(
                        authorizationServerProperties.getClientId(),
                        authorizationServerProperties.getClientSecret()
                ));

        try {
            ResponseEntity<Map> responseEntity = restTemplate.postForEntity(authorizationServerProperties.getTokenUrl(), requestEntity, Map.class);

            String accessToken = responseEntity.getBody().get("access_token").toString();

            this.accessToken = accessToken;

            return accessToken;
        }
        catch(RestClientException restClientException) {
            logger.error(restClientException.toString());
            throw restClientException;
        }
    }
}
