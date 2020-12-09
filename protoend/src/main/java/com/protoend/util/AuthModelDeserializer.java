package com.protoend.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.protoend.auth.model.*;
import com.protoend.auth.model.AuthType;
import com.protoend.base.util.exceptions.ProtoEndException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.protoend.base.util.ProtoEndUtil.getObjectMapper;


public class AuthModelDeserializer extends JsonDeserializer {

    private static final Logger logger = LoggerFactory.getLogger(AuthModelDeserializer.class);

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {

        try {
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
                case TOKEN:
                    authModel = getObjectMapper().convertValue(node, Token.class);
                    break;
                case CUSTOM:
                    //TODO Need to implement custom logic
                default:
                    throw new ProtoEndException("Invalid Auth type, please choose valid", 500);
            }

            return authModel;
        }catch (IOException | IllegalArgumentException e){
            logger.error(e.getMessage(), e);
            throw new ProtoEndException("Unable to serialize Auth data!!!", 500);
        }
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
                throw new ProtoEndException("Invalid Auth type, please choose valid", 400);
        }

        return authModel;
    }

}
