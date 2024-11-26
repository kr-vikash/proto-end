package com.protoend.base.util.exceptions;

public class RequiredObjectException extends ProtoEndException {

    public RequiredObjectException(String msg, int status) {
        super(msg, status);
    }

    public RequiredObjectException(String message, Throwable cause) {
        super(message, cause);
    }
}
