package com.protoend.auth.authenticator;

import com.protoend.auth.model.AuthModel;
import com.protoend.auth.model.OAuth;
import com.protoend.base.util.exceptions.ProtoEndException;

import java.util.Map;

public class OAuthAuthenticator extends Authenticator {

    public String accessToken;

    public OAuthAuthenticator(AuthModel authModel,
                              Map<String, Object> headers,
                              Map<String, Object> queryParam,
                              Map<String, Object> routeParam) {
        super(authModel, headers, queryParam, routeParam);
    }

    public void processAuth() {
        //TODO implement oauth logic
    }
}
