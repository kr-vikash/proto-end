package com.protoend.util.exceptions;

import com.protoend.base.util.exceptions.ProtoEndException;

public class ProtoConnectionException extends ProtoEndException {
    public ProtoConnectionException(String msg, int status) {
        super(msg, status);
    }

    public ProtoConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}