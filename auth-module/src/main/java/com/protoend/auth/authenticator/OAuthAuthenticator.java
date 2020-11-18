package com.protoend.auth.authenticator;

import com.protoend.auth.model.AuthModel;
import com.protoend.auth.model.OAuth;
import com.protoend.base.util.exceptions.ProtoEndException;

import java.util.Map;

public class OAuthAuthenticator extends Authenticator {

    private final OAuth oauthModel;

    public String accessToken;

    public OAuthAuthenticator(AuthModel authModel,
                              Map<String, Object> headers,
                                  Map<String, Object> queryParam,
                              Map<String, Object> routeParam){
        try {
            this.oauthModel = (OAuth) authModel;
        }catch (NullPointerException | ClassCastException e){
            throw new ProtoEndException("Invalid Authentication data", 400);
        }
        this.headers = headers;
        this.queryParam = queryParam;
        this.routeParam = routeParam;
    }

    public void processAuth() {
//        this.addHeader("Authorization", "Basic "+getEncodedCredentials(oauthModel.getClientId(), oauthModel.getClientSecret()));
//        this.addQueryParam("code", );
    }
}
