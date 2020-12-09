package com.protoend.auth.model;


import com.sun.istack.internal.NotNull;
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

    public OAuth(@NotNull String clientId, @NotNull String clientSecret, String tokenName, String tokenUrl, String scope, String grantType){
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.grantType = grantType;
        this.scope = scope;
        this.tokenName = tokenName;
        this.tokenUrl = tokenUrl;
    }

    @Override
    public String authValue() {
        return null;
    }
}
