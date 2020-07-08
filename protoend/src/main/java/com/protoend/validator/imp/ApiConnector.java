package com.protoend.validator.imp;

import com.protoend.auth.authenticator.Authenticator;
import com.protoend.model.Response;
import com.protoend.model.dto.ProtoEndDto;
import com.protoend.util.BeanUtil;
import com.protoend.validator.ProtoConnector;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

public class ApiConnector extends ProtoConnector {

    private RestTemplate restTemplate;

    public ApiConnector(Authenticator authenticator, ProtoEndDto protoEndDto) {
        this.setAuthenticator(authenticator);
        this.setProtoEndDto(protoEndDto);
        this.restTemplate = BeanUtil.getBean(RestTemplate.class);
    }

    @Override
    public ResponseEntity<Response> connect() {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.setAll(this.getAuthenticator().getHeaders() != null ? this.getAuthenticator().getHeaders() : new HashMap<>());
        HttpEntity<Object> request = new HttpEntity<>(this.getProtoEndDto().getRequestDetail().getRequestBody(), headers);
        Response<String> response = null;
        HttpStatus statusCode = null;
        try {
            ResponseEntity<String> restResponse = restTemplate.exchange(this.getProtoEndDto().getUrl(), this.getProtoEndDto().getRequestDetail().getMethod(), request, String.class);
            response = new Response<>(restResponse.getBody(), restResponse.getHeaders());
            statusCode = restResponse.getStatusCode();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            response = new Response<>(e.getMessage());
            statusCode = e.getStatusCode();
        }

        return new ResponseEntity<Response>(response, statusCode);
    }
}