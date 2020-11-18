package com.protoend.validator;

import com.protoend.auth.authenticator.Authenticator;
import com.protoend.base.util.exceptions.ProtoEndException;
import com.protoend.model.Response;
import com.protoend.model.dto.ProtoEndDto;
import org.springframework.http.ResponseEntity;

public abstract class ProtoConnector {

    private Authenticator authenticator;
    private ProtoEndDto protoEndDto;

    public ResponseEntity<Response> connect() {
       return null;
    }

    public Authenticator getAuthenticator() {
        return authenticator;
    }

    public void setAuthenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    public ProtoEndDto getProtoEndDto() {
        return protoEndDto;
    }

    public void setProtoEndDto(ProtoEndDto protoEndDto) {
        this.protoEndDto = protoEndDto;
    }
}
