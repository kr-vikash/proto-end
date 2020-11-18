package com.protoend.base.util.exceptions;

public class ProtoEndException extends RuntimeException {
    private int status;
    public ProtoEndException (String msg, int status){
        super(msg);
        this.status = status;
    }

    public ProtoEndException(String message, Throwable cause) {
        super(message, cause);
    }

    public int getStatus() {
        return status;
    }
}
