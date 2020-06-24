package com.protoend.auth.authenticator;

import com.protoend.base.util.Constants;
import lombok.Getter;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class Authenticator {
    Map<String, String> headers = null;
    Map<String, String> queryParam = null;
//    String authorizationKey = "Authorization";

    public void addHeader(String key, String val) {
        if (headers == null) {
            headers = new HashMap<>();
        }
        this.headers.put(key, val);
    }

    public void addQueryParam(String key, String val) {
        if (queryParam == null) {
            queryParam = new HashMap<>();
        }
        this.queryParam.put(key, val);
    }

    public void processAuth(){}

    public String getEncodedCredentials(String username, String password){
        String credentials = new StringBuilder(username).append(Constants.COLON).append(password).toString();
        return new String(Base64.getEncoder().encode(credentials.getBytes()));
    }
}