package com.protoend.validator.imp;

import com.jcraft.jsch.*;
import com.protoend.auth.authenticator.Authenticator;
import com.protoend.auth.authenticator.BasicAuthenticator;
import com.protoend.auth.model.Basic;
import com.protoend.base.util.exceptions.DataFormatException;
import com.protoend.base.util.exceptions.ProtoEndException;
import com.protoend.model.Response;
import com.protoend.model.dto.ProtoEndDto;
import com.protoend.util.BeanUtil;
import com.protoend.util.exceptions.ProtoConnectionException;
import com.protoend.validator.ProtoConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

import static com.protoend.base.util.ProtoEndUtil.getObjectMapper;

/**
 * FTPConnector used to connect FTP/SFTP shell request
 */

public class FTPConnector extends ProtoConnector {

    private static final Logger logger = LoggerFactory.getLogger(FTPConnector.class);

    private final JSch ftpClient;
    private final Basic basicAuth;
    private Session session;

    public FTPConnector(Authenticator authenticator, ProtoEndDto protoEndDto) {
        if (authenticator instanceof BasicAuthenticator) {
            this.basicAuth = ((BasicAuthenticator) authenticator).basicModel;
        } else {
            throw new ProtoEndException("FTP/SFTP only allowed basic authentication");
        }
        this.ftpClient = BeanUtil.getBean(JSch.class);
        this.setProtoEndDto(protoEndDto);

    }

    /**
     * @return ResponseEntity
     * @throws ProtoConnectionException Unable to achieve ftp/sftp connection, due to socket exception or UnknownHost
     * @throws DataFormatException      if Any IOException exceptions caught
     */
    @Override
    public ResponseEntity<Response> connect() {

        String host = this.getProtoEndDto().getUrl();
        Map<String, Object> addProperties = this.getProtoEndDto().getAdditionalProperties();
        int port = 22;
        try {
            if (addProperties != null &&
                    addProperties.containsKey("port")) {
                port = new Integer(String.valueOf(addProperties.get("port")));
            }
        } catch (NumberFormatException e) {
            throw new DataFormatException("Invalid port value found!!!, Required format:Integer");
        }
        Object uploadFileVal = this.getProtoEndDto().getRequestDetail().getRequestBody();
        String pathVal = (addProperties == null || addProperties.get("pathVariable") == null) ? null : String.valueOf(addProperties.get("pathVariable"));
        String fileName = (addProperties == null || addProperties.get("fileName") == null) ? null : String.valueOf(addProperties.get("fileName"));
        HttpMethod method = this.getProtoEndDto().getRequestDetail().getMethod();
        Response<Object> response = new Response<>();
        HttpStatus status = HttpStatus.OK;
        try {
            ChannelSftp channelSftp = establishConnection(host, port);
            if (pathVal != null){
                channelSftp.cd(pathVal);
            }
            switch (method) {
                case GET:
                    if (fileName != null){
                        channelSftp.get(fileName);
                    }else {
                        response.setBody(channelSftp.ls("."));
                    }
                    break;
                case POST:
                    channelSftp.put(new ByteArrayInputStream(getObjectMapper().writeValueAsBytes(uploadFileVal)), fileName);
                    response.setBody("File successfully uploaded");
                    break;
                default:
                    throw new ProtoEndException("Invalid method for SFTP connection");
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new DataFormatException(e.getMessage());
        } catch (SftpException e) {
            logger.error(e.getMessage(), e);
            throw new ProtoEndException("Unable to access folder/file from the server");
        } finally {
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
        return new ResponseEntity<>(response, status);

    }

    private ChannelSftp establishConnection(String host, int port) {
        try {
            session = ftpClient.getSession(basicAuth.getUsername(), host);
            // 5 min timeout
            session.setTimeout(300000);
            session.setPort(port);
            session.setPassword(basicAuth.getPassword());
            session.connect();
            logger.info("Session created !!!");
            return (ChannelSftp) session.openChannel("sftp");
        } catch (JSchException e) {
            logger.error(e.getMessage(), e);
            throw new ProtoConnectionException("Unable to establish the FTP/SFTP connection:" + e.getMessage());
        }
    }
}