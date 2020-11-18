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

    public static byte[] objectToBytes(Object object) throws ProtoEndException {
        try {
            return getObjectMapper().writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            throw new ProtoEndException("Issue while parsing object to bytes", 500);
        }
    }

    public static <T> T  notNull(T t, String propertyName) throws RequiredObjectException {
        if (t == null){
            throw new RequiredObjectException( propertyName + " can not be null !!!", 500);
        }
        return t;
    }

    public static void notNullAndNotEmpty(String val, String propertyName) throws RequiredObjectException {
        if (val == null || val.trim().isEmpty()){
            throw new RequiredObjectException(propertyName + " can not be null or empty!!!", 500);
        }
    }

    public static String getString(Object obj) {
        if (obj != null) {
            return String.valueOf(obj);
        }
        return null;
    }
}
