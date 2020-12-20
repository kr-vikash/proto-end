package com.protoend.auth.model;


import com.protoend.base.util.Constants;
import lombok.Getter;
import lombok.Setter;

import java.util.Base64;

import static com.protoend.auth.utils.AuthConstant.BASIC;

@Getter
@Setter
public class Basic extends AuthModel {

    private String username;
    private String password;

    public Basic(){}

    public Basic(String username, String password, AuthType authType){
        this.username = username;
        this.password = password;
        this.authType = AuthType.BASIC;
    }

    @Override
    public String authValue() {
        String basicAuthString = new StringBuilder(username)
                .append(Constants.COLON)
                .append(password).toString();

        String encodedAuthValue = Base64.getEncoder().encodeToString(basicAuthString.getBytes());
        return new StringBuilder(BASIC)
                .append(Constants.WHITE_SPACE)
                .append(encodedAuthValue).toString();

    }
}