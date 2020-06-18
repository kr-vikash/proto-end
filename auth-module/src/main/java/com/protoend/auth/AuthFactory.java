package com.protoend.auth;

import com.protoend.auth.authenticator.Authenticator;
import com.protoend.auth.authenticator.BasicAuthenticator;
import com.protoend.auth.authenticator.NoAuthenticator;
import com.protoend.auth.authenticator.OAuthAuthenticator;
import com.protoend.base.model.AuthModel;
import com.protoend.base.util.exceptions.ProtoEndException;

import java.util.Map;

public class AuthFactory {


    public static Authenticator getAuthenticator(AuthModel authModel, Map<String, String> headers, Map<String, String> queryParam) {

        Authenticator authenticator;
        switch (authModel.authType){

            case BASIC:
                authenticator = new BasicAuthenticator(authModel, headers, queryParam);
                break;
            case NO_AUTH:
                authenticator = new NoAuthenticator(authModel, headers, queryParam);
                break;
            case OAUTH:
                authenticator = new OAuthAuthenticator(authModel, headers, queryParam);
                break;
            default:
                throw new ProtoEndException("The Authentication type"+ authModel.authType+" is not supported!!!");
        }
        return authenticator;

    }
}