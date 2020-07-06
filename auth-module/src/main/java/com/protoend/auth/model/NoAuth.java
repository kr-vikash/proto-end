package com.protoend.auth.model;

import com.protoend.base.model.enumerator.AuthType;

public class NoAuth extends AuthModel {

    public NoAuth(){};

    public NoAuth(AuthType authType){
        this.authType = authType;
    }

}
