package com.protoend.auth.authenticator;

import com.protoend.auth.model.AuthModel;
import com.protoend.base.util.Constants;
import lombok.Getter;

import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.protoend.auth.utils.AuthConstant.AUTHORIZATION;

@Getter
public abstract class Authenticator {
    private Map<String, Object> headers;
    private Map<String, Object> queryParam;
    private Map<String, Object> routeParam;
    private AuthModel authModel;

    public Authenticator(AuthModel authModel,
                         Map<String, Object> headers,
                         Map<String, Object> queryParam,
                         Map<String, Object> routeParam) {
        this.authModel = authModel;
        this.headers = headers;
        this.queryParam = queryParam;
        this.routeParam = routeParam;
    }

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

    public void processAuth(){
        this.addHeader(AUTHORIZATION, authModel.authValue());
    }
}