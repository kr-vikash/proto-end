package com.protoend.auth.model;


import com.protoend.base.model.enumerator.AuthType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Basic extends AuthModel {

    private String username;
    private String password;
    private String authorizationKey = "Authorization";
    private String authValueType = "Basic";

    public Basic(){};

    public Basic(String username, String password, AuthType authType, String authorizationKey){
        this.username = username;
        this.password = password;
        this.authType = authType;
        this.authorizationKey = (authorizationKey == null || authorizationKey.isEmpty()) ? "Authorization": authorizationKey;
    }

}