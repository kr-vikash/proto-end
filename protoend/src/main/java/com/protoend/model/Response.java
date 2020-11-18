package com.protoend.model;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {
    private T body;
    private HttpHeaders headers;
    private Map<String, String> additonalInfo;
    private int status;

    public Response(T body, int status) {
        this.body = body;
    }

    public Response(T body, HttpHeaders headers, int status) {
        this.body = body;
        this.headers = headers;
    }

    public Response(T body, Map<String, String> additonalInfo, int status) {
        this.body = body;
        this.additonalInfo = additonalInfo;
    }
}
