package com.protoend.model.dto;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.protoend.base.model.AuthModel;
import com.protoend.base.model.Basic;
import com.protoend.base.model.NoAuth;
import com.protoend.base.model.OAuth;
import com.protoend.base.model.enumerator.AuthType;
import com.protoend.base.model.enumerator.ConnectionType;
import com.protoend.base.util.ProtoEndUtil;
import com.protoend.base.util.exceptions.ProtoEndException;
import com.protoend.model.ProtoEnd;
import com.protoend.model.RequestDetail;
import com.protoend.util.AuthModelDerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Getter
@Setter
@AllArgsConstructor
public class ProtoEndDto {

    private int id;

    private ConnectionType connectionType;

    private String url;

    private RequestDetail requestDetail;

    private AuthType authType;

    @JsonDeserialize(using = AuthModelDerializer.class)
    private AuthModel authModel;


    public ProtoEnd entityMapper() {
        ProtoEnd protoTest = new ProtoEnd();
        protoTest.setId(this.id);
        protoTest.setUrl(this.url);
        protoTest.setConnectionType(this.connectionType);
        protoTest.setRequestDetail(ProtoEndUtil.objectToBytes(this.requestDetail));
        protoTest.setAuth(ProtoEndUtil.objectToBytes(this.authModel));
        protoTest.setAuthType(this.authType);
        return protoTest;
    }

    public ProtoEndDto() {
    }

    public ProtoEndDto(ProtoEnd protoTest) throws IOException {

        this.id = protoTest.getId();
        this.requestDetail = ProtoEndUtil.getObjectMapper().readValue(protoTest.getRequestDetail(), RequestDetail.class);
        this.url = protoTest.getUrl();
        this.connectionType = protoTest.getConnectionType();
        this.authType = protoTest.getAuthType();

        switch (authType) {
            case BASIC:
                this.authModel = ProtoEndUtil.getObjectMapper().readValue(protoTest.getAuth(), Basic.class);
                break;
            case OAUTH:
                this.authModel = ProtoEndUtil.getObjectMapper().readValue(protoTest.getAuth(), OAuth.class);
                break;
            case CUSTOM:
                this.authModel = ProtoEndUtil.getObjectMapper().readValue(protoTest.getAuth(), Basic.class);
                break;
            case NO_AUTH:
                this.authModel = ProtoEndUtil.getObjectMapper().readValue(protoTest.getAuth(), NoAuth.class);
                break;

            default:
                throw new ProtoEndException("Invalid AUTH");
        }
    }
}