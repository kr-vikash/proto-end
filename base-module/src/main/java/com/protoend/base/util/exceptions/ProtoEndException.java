package com.protoend.base.util.exceptions;

public class ProtoEndException extends RuntimeException {

    public ProtoEndException (String msg){
        super(msg);
    }

    public ProtoEndException(String message, Throwable cause) {
        super(message, cause);
    }

}
