package com.protoend.base.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OAuth extends AuthModel {
    private String clientId;
    private String clientSecret;
    private String tokenName;
    private String tokenUrl;
    private String scope;
    private String grantType;
    private String redirectUrl;

    public OAuth(String clientId, String clientSecret, String tokenName, String tokenUrl, String scope, String grantType){
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.grantType = grantType;
        this.scope = scope;
        this.tokenName = tokenName;
        this.tokenUrl = tokenUrl;
    }

}
