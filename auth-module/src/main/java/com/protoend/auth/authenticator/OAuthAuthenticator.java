package com.protoend.auth.authenticator;

import com.protoend.base.model.AuthModel;
import com.protoend.base.model.OAuth;
import com.protoend.base.util.exceptions.ProtoEndException;

import java.util.Map;

public class OAuthAuthenticator extends Authenticator {

    private OAuth oauthModel;

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
}
