package com.protoend.auth.model;


import com.protoend.base.model.enumerator.AuthType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Basic extends AuthModel {

    private String username;
    private String password;

    public Basic(){}

    public Basic(String username, String password, AuthType authType){
        this.username = username;
        this.password = password;
        this.authType = authType;
    }

}