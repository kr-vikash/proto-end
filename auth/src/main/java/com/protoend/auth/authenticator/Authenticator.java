package com.protoend.auth.authenticator;

import java.util.HashMap;
import java.util.Map;

public abstract class Authenticator {
    Map<String, Object> headers = null;
    Map<String, Object> queryParam = null;
    String authorizationKey = "Authorization";

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
}