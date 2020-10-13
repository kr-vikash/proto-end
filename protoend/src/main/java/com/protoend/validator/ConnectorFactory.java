package com.protoend.validator;

import com.protoend.auth.authenticator.Authenticator;
import com.protoend.base.util.exceptions.ProtoEndException;
import com.protoend.model.dto.ProtoEndDto;
import com.protoend.validator.imp.ApiConnector;
import com.protoend.validator.imp.FTPConnector;

public class ConnectorFactory {

    public static ProtoConnector getConnector(Authenticator authenticator, ProtoEndDto protoEndDto) {
        ProtoConnector protoConnector = null;

        switch (protoEndDto.getConnectionType()) {

            case SOAP:
                protoConnector = new ApiConnector(authenticator, protoEndDto);
                break;
            case REST:
                protoConnector = new ApiConnector(authenticator, protoEndDto);
                break;
            case FTP:
            case SFTP:
                protoConnector = new FTPConnector(authenticator, protoEndDto);
                break;
            default:
                throw new ProtoEndException("Not Implemented yet");
        }
        return protoConnector;

    }
}
