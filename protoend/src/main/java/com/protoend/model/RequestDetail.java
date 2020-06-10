package com.protoend.model;

import com.protoend.model.enumerator.AuthType;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;


@Getter
@Setter
public class RequestDetail {
    private String method;
    private AuthType authType;
    private Map<String, Object> headers;
    private Map<String, Object> queryParameter;
    private String requestBody;
}
