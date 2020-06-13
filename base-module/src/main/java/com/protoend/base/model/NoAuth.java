package com.protoend.base.model;

import com.protoend.base.model.enumerator.AuthType;

public class NoAuth extends AuthModel {

    public NoAuth(AuthType authType){
        this.authType = authType;
    }

}
