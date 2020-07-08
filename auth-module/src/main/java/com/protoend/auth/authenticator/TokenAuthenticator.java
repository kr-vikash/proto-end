package com.protoend.auth.authenticator;

import com.protoend.auth.model.AuthModel;
import com.protoend.auth.model.Token;
import com.protoend.base.util.Constants;
import com.protoend.base.util.exceptions.ProtoEndException;

import java.util.Map;

public class TokenAuthenticator extends Authenticator {

    private final Token token;

    public TokenAuthenticator(AuthModel authModel,
                              Map<String, String> headers,
                              Map<String, String> queryParam){

        try {
            this.token = (Token) authModel;
        }catch (ClassCastException e){
            throw new ProtoEndException("Invalid Authentication data");
        }
        this.headers = headers;
        this.queryParam = queryParam;
    }

    public void processAuth(){
        String authHeaderVal = new StringBuilder(token.getTokenType())
                .append(Constants.WHITE_SPACE)
                .append(token.getTokenValue()).toString();
        this.addHeader(token.getAuthorizationKey(), authHeaderVal);
    }

}
