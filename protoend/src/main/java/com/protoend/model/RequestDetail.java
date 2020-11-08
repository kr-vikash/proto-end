package com.protoend.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;

import java.util.Map;


@Getter
@Setter
public class RequestDetail {
    private HttpMethod method;
    private Map<String, Object> headers;
    private Map<String, Object> queryParameter;
    private Map<String, Object> routeParameter;
    private Object requestBody;
}