package com.protoend.auth.authenticator;

import com.protoend.base.model.AuthModel;
import com.protoend.base.model.OAuth;
import com.protoend.base.util.exceptions.ProtoEndException;

import java.util.Map;

public class OAuthAuthenticator extends Authenticator {

    private final OAuth oauthModel;

    public String accessToken;

    public OAuthAuthenticator(AuthModel authModel,
                              Map<String, String> headers,
                              Map<String, String> queryParam){
        try {
            this.oauthModel = (OAuth) authModel;
        }catch (NullPointerException | ClassCastException e){
            throw new ProtoEndException("Invalid Authentication data");
        }
        this.headers = headers;
        this.queryParam = queryParam;
    }

    public void processAuth() {
//        this.addHeader("Authorization", "Basic "+getEncodedCredentials(oauthModel.getClientId(), oauthModel.getClientSecret()));
//        this.addQueryParam("code", );
    }
}
