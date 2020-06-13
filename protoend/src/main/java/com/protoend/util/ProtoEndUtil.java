package com.protoend.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.protoend.util.exceptions.ProtoEndException;

public class ProtoEndUtil {

    private static ObjectMapper objectMapper;

    public static ObjectMapper getObjectMapper(){
        if (objectMapper == null){
            objectMapper = new ObjectMapper();
        }
        return objectMapper;
    }

    public static byte[] objectToBytes(Object object){
        try {
            return getObjectMapper().writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            throw new ProtoEndException("Issue while parsing object to bytes");
        }
    }
}
