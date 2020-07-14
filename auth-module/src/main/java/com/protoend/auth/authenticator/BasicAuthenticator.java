package com.protoend.auth.authenticator;

import com.protoend.auth.model.AuthModel;
import com.protoend.auth.model.Basic;
import com.protoend.base.util.Constants;
import com.protoend.base.util.exceptions.ProtoEndException;

import java.util.Base64;
import java.util.Map;

public class BasicAuthenticator extends Authenticator {

    public final Basic basicModel;

    public BasicAuthenticator(AuthModel authModel,
                              Map<String, String> headers,
                              Map<String, String> queryParam){
        try {
            this.basicModel = (Basic) authModel;
        }catch (NullPointerException | ClassCastException e){
            throw new ProtoEndException("Invalid Authentication data");
        }
        this.headers = headers;
        this.queryParam = queryParam;
    }

    public void processAuth(){
        if (basicModel.getPassword() == null && basicModel.getUsername() == null){
            throw new NullPointerException("Username and password can not be null");
        }
        String authValue = new StringBuilder(basicModel.getUsername())
                .append(Constants.COLON)
                .append(basicModel.getPassword()).toString();
        byte[] encodedAuthValue = Base64.getEncoder().encode(authValue.getBytes());
        this.addHeader(basicModel.getAuthorizationKey(), new StringBuilder(basicModel.getAuthValueType())
                .append(Constants.WHITE_SPACE)
                .append(new String(encodedAuthValue)).toString());
    }

}
