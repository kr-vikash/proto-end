package com.protoend.base.util.exceptions;

public class RequiredObjectException extends ProtoEndException {

    public RequiredObjectException(String msg) {
        super(msg);
    }

    public RequiredObjectException(String message, Throwable cause) {
        super(message, cause);
    }
}
