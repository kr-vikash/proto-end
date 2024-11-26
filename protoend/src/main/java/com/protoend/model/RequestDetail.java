package com.protoend.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;

import java.util.Map;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestDetail {
    private HttpMethod method;
    private Map<String, Object> headers;
    private Map<String, Object> queryParameter;
    private Map<String, Object> routeParameter;
    private Object requestBody;
}