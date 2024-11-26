package com.protoend.auth.authenticator;

import com.protoend.auth.model.AuthModel;
import com.protoend.auth.model.Token;
import com.protoend.base.util.Constants;
import com.protoend.base.util.exceptions.ProtoEndException;

import java.util.Map;

public class TokenAuthenticator extends Authenticator {

    public TokenAuthenticator(AuthModel authModel,
                              Map<String, Object> headers,
                              Map<String, Object> queryParam,
                              Map<String, Object> routeParam){

        super(authModel, headers, queryParam, routeParam);
    }

}