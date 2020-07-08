package com.protoend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpHeaders;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
    private T body;
    private HttpHeaders headers;
    private Map<String, String> additonalInfo;

    public Response(T body) {
        this.body = body;
    }

    public Response(T body, HttpHeaders headers) {
        this.body = body;
        this.headers = headers;
    }

    public Response(T body, Map<String, String> additonalInfo) {
        this.body = body;
        this.additonalInfo = additonalInfo;
    }
}
