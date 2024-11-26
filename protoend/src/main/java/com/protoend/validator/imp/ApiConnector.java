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
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

import static com.protoend.base.util.ProtoEndUtil.getString;

public class ApiConnector extends ProtoConnector {

    private RestTemplate restTemplate;

    public ApiConnector(Authenticator authenticator, ProtoEndDto protoEndDto) {
        this.setAuthenticator(authenticator);
        this.setProtoEndDto(protoEndDto);
        this.restTemplate = BeanUtil.getBean(RestTemplate.class);
    }

    @Override
    public ResponseEntity<Response> connect() {
        MultiValueMap<String, String> headers = processHeaders(this.getAuthenticator().getHeaders());
        HttpEntity<Object> request = new HttpEntity<>(this.getProtoEndDto().getRequestDetail().getRequestBody(), headers);
        Response<String> response = null;
        HttpStatus statusCode = null;
        try {
            ResponseEntity<String> restResponse = restTemplate.exchange(buildURI(), this.getProtoEndDto().getRequestDetail().getMethod(), request, String.class);
            statusCode = restResponse.getStatusCode();
            response = new Response<>(restResponse.getBody(), restResponse.getHeaders(), statusCode.value());
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            response = new Response<>(e.getMessage(), e.getStatusCode().value());
            statusCode = e.getStatusCode();
        }

        return new ResponseEntity<>(response, statusCode);
    }

    private URI buildURI() {
        Map<String, Object> queryParam = this.getAuthenticator().getQueryParam();
        Map<String, Object> routeParam = this.getAuthenticator().getRouteParam();

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(this.getProtoEndDto()
                .getUrl());
        parseQueryParams(uriComponentsBuilder, queryParam);

        if (routeParam != null && !routeParam.isEmpty()) {
            uriComponentsBuilder.uriVariables(routeParam);
        }

        return uriComponentsBuilder.build().encode().toUri();

    }

    private MultiValueMap<String, Object> mapToMultivalueMap(Map<String, Object> map) {

        MultiValueMap<String, Object> multivalMap = new LinkedMultiValueMap<>();
        if(map == null || map.isEmpty()){
            return multivalMap;
        }
        multivalMap.setAll(map);
        return multivalMap;
    }

    private MultiValueMap<String, String> processHeaders(Map<String, Object> map) {

        MultiValueMap<String, String> multivalMap = new LinkedMultiValueMap<>();
        if (map != null && map.isEmpty()) {

            for (Map.Entry<String, Object> entry : map.entrySet()) {
                multivalMap.set(entry.getKey(), getString(entry.getValue()));
            }
        }

        return multivalMap;
    }

    private void parseQueryParams(UriComponentsBuilder componentsBuilder, Map<String, Object> queryParam) {

        if (queryParam == null || queryParam.isEmpty()) {
            return;
        }

        for (Map.Entry<String, Object> entry : queryParam.entrySet()) {
            componentsBuilder.queryParam(entry.getKey(), entry.getValue());
        }

    }
}