package com.protoend.model;

import com.protoend.model.enumerator.ConnectionType;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ProtoTest {
    private ConnectionType connectionType;
    private String url;
    private RequestDetail requestDetail;
    private Map<String, Object> additionalProperty;
}
