package com.protoend.auth.model;

public class NoAuth extends AuthModel {

    public NoAuth(){};

    public NoAuth(AuthType authType){
        this.authType = AuthType.NO_AUTH;
    }

    @Override
    public String authValue() {
        return null;
    }
}
