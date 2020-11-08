package com.protoend.auth.authenticator;

import com.protoend.auth.model.AuthModel;

import java.util.Map;

public class NoAuthenticator extends Authenticator{

        public NoAuthenticator(AuthModel authModel, Map<String, Object> headers, Map<String, Object> queryParam, Map<String, Object> routeParam){
        this.headers = headers;
        this.queryParam = queryParam;
        this.routeParam = routeParam;
    }
}
