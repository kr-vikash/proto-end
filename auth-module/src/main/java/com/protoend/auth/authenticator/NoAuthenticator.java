package com.protoend.auth.authenticator;

import com.protoend.auth.model.AuthModel;

import java.util.Map;

public class NoAuthenticator extends Authenticator{

    public NoAuthenticator(AuthModel authModel, Map<String, String> headers, Map<String, String> queryParam){
        this.headers = headers;
        this.queryParam = queryParam;
    }
}
