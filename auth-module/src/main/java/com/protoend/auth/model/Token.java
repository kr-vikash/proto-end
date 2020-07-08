package com.protoend.auth.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Token extends AuthModel {
    private String tokenValue;
    private String authorizationKey = "Authorization";
    private String tokenType = "Basic";

    public Token(){}

    public Token(String tokenValue, String authorizationKey, String tokenType) {
        this.tokenValue = tokenValue;
        this.authorizationKey = authorizationKey != null ? authorizationKey : this.authorizationKey;
        this.tokenType = tokenType != null ? tokenType : this.tokenType;
    }
}