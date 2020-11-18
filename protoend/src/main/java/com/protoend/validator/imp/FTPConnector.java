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
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static com.protoend.base.util.Constants.STRICT_KEY_CHECK_DISABLE;
import static com.protoend.base.util.ProtoEndUtil.getObjectMapper;

/**
 * FTPConnector used to connect FTP/SFTP shell request
 */

public class FTPConnector extends ProtoConnector {

    private static final Logger logger = LoggerFactory.getLogger(FTPConnector.class);

    private final JSch ftpClient;
    private final Basic basicAuth;
    private Session session;

    private static final String PATH = "path";
    private static final String FILENAME = "fileName";
    public static final String PORT ="port";

    public FTPConnector(Authenticator authenticator, ProtoEndDto protoEndDto) throws ProtoEndException {
        if (authenticator instanceof BasicAuthenticator) {
            this.basicAuth = ((BasicAuthenticator) authenticator).basicModel;
        } else {
            throw new ProtoEndException("FTP/SFTP only allowed basic authentication", HttpStatus.BAD_REQUEST.value());
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
    public ResponseEntity<Response> connect() throws ProtoEndException {

        String host = this.getProtoEndDto().getUrl();
        Map<String, Object> addProperties = this.getProtoEndDto().getAdditionalProperties();
        int port = 22;
        try {
            if (addProperties != null &&
                    addProperties.containsKey(PORT)) {
                port = new Integer(String.valueOf(addProperties.get(PORT)));
            }
        } catch (NumberFormatException e) {
            throw new DataFormatException("Invalid port value found!!!, Required format:Integer", HttpStatus.BAD_REQUEST.value());
        }

        Object uploadFileVal = this.getProtoEndDto().getRequestDetail().getRequestBody();
        String pathVal = (addProperties == null || addProperties.get(PATH) == null) ? null : String.valueOf(addProperties.get(PATH));
        String fileName = (addProperties == null || addProperties.get(FILENAME) == null) ? null : String.valueOf(addProperties.get(FILENAME));
        HttpMethod method = this.getProtoEndDto().getRequestDetail().getMethod();
        Response<Object> response = new Response<>();
        HttpStatus status = HttpStatus.OK;

        try {
            ChannelSftp channelSftp = establishConnection(host, port);
            logger.info("SFTP/FTP connection status: ", channelSftp.isConnected());
            if (pathVal != null) {
                channelSftp.cd(pathVal);
            }
            switch (method) {
                case GET:
                    if (fileName != null) {
                        response.setBody(inputStreamToString(channelSftp.get(fileName)));
                    } else {
                        response.setBody(channelSftp.ls("."));
                    }
                    break;
                case POST:
                    channelSftp.put(new ByteArrayInputStream(getObjectMapper().writeValueAsBytes(uploadFileVal)), fileName);
                    response.setBody("File successfully uploaded");
                    break;
                default:
                    throw new ProtoEndException("Invalid method for SFTP connection", HttpStatus.BAD_REQUEST.value());
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new DataFormatException(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        } catch (SftpException e) {
            logger.error(e.getMessage(), e);
            throw new ProtoEndException("Unable to access folder/file from the server", HttpStatus.BAD_GATEWAY.value());
        } finally {
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
        return new ResponseEntity<>(response, status);

    }

    private ChannelSftp establishConnection(String host, int port) throws ProtoConnectionException {
        try {
            session = ftpClient.getSession(basicAuth.getUsername(), host);
            // 5 min timeout
            session.setTimeout(300000);
            session.setPort(port);
            session.setPassword(basicAuth.getPassword());
            session.setConfig(STRICT_KEY_CHECK_DISABLE, "no");
            session.connect();
            logger.info("Session created !!!");
            ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect(120000);
            return channelSftp;
        } catch (JSchException e) {
            logger.error(e.getMessage(), e);
            throw new ProtoConnectionException("Unable to establish the FTP/SFTP connection:" + e.getMessage(), HttpStatus.BAD_GATEWAY.value());
        }
    }

    private String inputStreamToString(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        IOUtils.copy(inputStream, byteArrayOutputStream);
        return new String(byteArrayOutputStream.toByteArray());
    }

}