package com.protoend.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;

import java.util.Map;


@Getter
@Setter
public class RequestDetail {
    private HttpMethod method;
    private Map<String, String> headers;
    private Map<String, String> queryParameter;
    private Object requestBody;
}