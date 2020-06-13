package com.protoend.auth.authenticator;

import com.protoend.base.model.AuthModel;

import java.util.Map;

public class NoAuthenticator extends Authenticator{

    public NoAuthenticator(AuthModel authModel, Map<String, Object> headers, Map<String, Object> queryParam){
        this.headers = headers;
        this.queryParam = queryParam;
    }
}
