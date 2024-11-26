package com.protoend.auth.model;


import lombok.Getter;
import lombok.Setter;

import static com.protoend.auth.utils.AuthConstant.BASIC;
import static com.protoend.base.util.Constants.WHITE_SPACE;

@Getter
@Setter
public class Token extends AuthModel {
    private String tokenValue;
    private String tokenType;

    public Token() {
    }

    public Token(String tokenValue, String tokenType, AuthType authType) {
        this.tokenValue = tokenValue;
        this.tokenType = tokenType != null ? tokenType : BASIC;
        this.authType = AuthType.TOKEN;
    }

    @Override
    public String authValue() {
        return new StringBuilder(tokenType).append(WHITE_SPACE).append(tokenValue).toString();
    }
}