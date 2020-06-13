package com.protoend.model.dto;


import com.protoend.model.ProtoTest;
import com.protoend.model.RequestDetail;
import com.protoend.model.enumerator.ConnectionType;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

import static com.protoend.util.ProtoEndUtil.getObjectMapper;
import static com.protoend.util.ProtoEndUtil.objectToBytes;

@Getter
@Setter
public class ProtoTestDto {

    private int id;

    private ConnectionType connectionType;

    private String url;

    private RequestDetail requestDetail;


    public ProtoTest entityMapper(){
        ProtoTest protoTest = new ProtoTest();
        protoTest.setId(this.id);
        protoTest.setUrl(this.url);
        protoTest.setConnectionType(this.connectionType);
        protoTest.setRequestDetail(objectToBytes(this.requestDetail));
        return protoTest;
    }

    public ProtoTestDto(){}

    public ProtoTestDto(ProtoTest protoTest) throws IOException {

        this.id = protoTest.getId();
        this.requestDetail = getObjectMapper().readValue(protoTest.getRequestDetail(), RequestDetail.class);
        this.url = protoTest.getUrl();
        this.connectionType = protoTest.getConnectionType();
    }
}
