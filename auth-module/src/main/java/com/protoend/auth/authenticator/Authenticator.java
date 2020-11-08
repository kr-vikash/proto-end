package com.protoend.auth.authenticator;

import com.protoend.auth.model.OAuth;
import com.protoend.base.util.Constants;
import lombok.Getter;

import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public abstract class Authenticator {
    Map<String, Object> headers = null;
    Map<String, Object> queryParam = null;
    Map<String, Object> routeParam = null;

    public void addHeader(String key, String val) {
        if (headers == null) {
            headers = new LinkedHashMap<>();
        }
        this.headers.put(key, val);
    }

    public void addQueryParam(String key, String val) {
        if (queryParam == null) {
            queryParam = new LinkedHashMap<>();
        }
        this.queryParam.put(key, val);
    }

    public void processAuth(){}

    public String getEncodedCredentials(String username, String password){
        String credentials = new StringBuilder(username).append(Constants.COLON).append(password).toString();
        return new String(Base64.getEncoder().encode(credentials.getBytes()));
    }

    public void addRouteParams(String key, String val) {
        if (routeParam == null) {
            routeParam = new LinkedHashMap<>();
        }
        this.routeParam.put(key, val);
    }
}