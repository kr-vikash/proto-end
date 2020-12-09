package com.protoend.auth.model;


public abstract class AuthModel {
    public AuthType authType;

    public abstract String authValue();
}