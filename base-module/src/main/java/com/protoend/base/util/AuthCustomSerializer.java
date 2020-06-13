package com.protoend.base.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.protoend.base.model.AuthModel;
import com.protoend.base.model.Basic;
import com.protoend.base.model.NoAuth;
import com.protoend.base.model.OAuth;
import com.protoend.base.model.enumerator.AuthType;
import com.protoend.base.util.exceptions.ProtoEndException;

import java.io.IOException;

import static com.protoend.base.util.ProtoEndUtil.getObjectMapper;


public class AuthCustomSerializer extends JsonDeserializer {

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        JsonNode node = jsonParser.readValueAsTree();
        AuthModel authModel;

        switch(AuthType.valueOf(node.get("authType").asText())){

            case BASIC:
              authModel = getObjectMapper().convertValue(node, Basic.class);
              break;
            case NO_AUTH:
                authModel = getObjectMapper().convertValue(node, NoAuth.class);
                break;
            case OAUTH:
                authModel = getObjectMapper().convertValue(node, OAuth.class);
                break;
            default:
                throw new ProtoEndException("Invalid Auth type, please choose valid");
        }

        return authModel;
    }

    @Override
    public AuthModel deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        AuthModel authModel;

        switch(AuthType.valueOf(node.get("authType").asText())){

            case BASIC:
                authModel = getObjectMapper().convertValue(node, Basic.class);
                break;
            case NO_AUTH:
                authModel = getObjectMapper().convertValue(node, NoAuth.class);
                break;
            case OAUTH:
                authModel = getObjectMapper().convertValue(node, OAuth.class);
                break;
            default:
                throw new ProtoEndException("Invalid Auth type, please choose valid");
        }

        return authModel;
    }

}
