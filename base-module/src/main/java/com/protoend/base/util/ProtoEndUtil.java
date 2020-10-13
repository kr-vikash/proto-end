package com.protoend.base.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.protoend.base.util.exceptions.ProtoEndException;
import com.protoend.base.util.exceptions.RequiredObjectException;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

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

    public static <T> T  notNull(T t, String propertyName){
        if (t == null){
            throw new RequiredObjectException( propertyName + " is required field to process the request !!!");
        }
        return t;
    }
}
