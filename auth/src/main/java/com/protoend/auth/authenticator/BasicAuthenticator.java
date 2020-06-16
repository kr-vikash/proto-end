package com.protoend.auth.authenticator;

import com.protoend.base.model.AuthModel;
import com.protoend.base.model.Basic;
import com.protoend.base.util.exceptions.ProtoEndException;

import java.util.Base64;
import java.util.Map;

public class BasicAuthenticator extends Authenticator {

    private Basic basicModel;

    public BasicAuthenticator(AuthModel authModel,
                              Map<String, Object> headers,
                              Map<String, Object> queryParam,
                              String authorizationKey){
        try {
            this.basicModel = (Basic) authModel;
        }catch (NullPointerException | ClassCastException e){
            throw new ProtoEndException("Invalid Authentication data");
        }
        this.headers = headers;
        this.queryParam = queryParam;
        this.authorizationKey = authorizationKey;
    }

    private void authHeader(){
        if (basicModel.getPassword() == null && basicModel.getUsername() == null){
            throw new NullPointerException("Username and password can not be null");
        }
        String authValue = basicModel.getUsername()+":"+basicModel.getPassword();
        byte[] encodedAuthValue = Base64.getEncoder().encode(authValue.getBytes());

        this.addHeader(authorizationKey, "Basic "+new String(encodedAuthValue));
    }

}
