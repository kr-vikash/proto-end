package com.protoend.base.util.exceptions;

public class DataFormatException extends ProtoEndException {

    public DataFormatException(String msg, int status) {
        super(msg, status);
    }
    public DataFormatException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
