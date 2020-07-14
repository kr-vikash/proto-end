package com.protoend.validator.imp;

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
import net.bytebuddy.utility.RandomString;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Map;

import static com.protoend.base.util.ProtoEndUtil.getObjectMapper;

/**
 * FTPConnector used to connect FTP/SFTP shell request
 */

public class FTPConnector extends ProtoConnector {

    private static final Logger logger = LoggerFactory.getLogger(FTPConnector.class);

    private final FTPClient ftpClient;
    private final Basic basicAuth;

    public FTPConnector(Authenticator authenticator, ProtoEndDto protoEndDto) {
        if (authenticator instanceof BasicAuthenticator) {
            this.basicAuth = ((BasicAuthenticator) authenticator).basicModel;
        } else {
            throw new ProtoEndException("FTP/SFTP only allowed basic authentication");
        }
        this.ftpClient = BeanUtil.getBean(FTPClient.class);
        this.setProtoEndDto(protoEndDto);
    }

    /**
     * @return ResponseEntity
     * @throws ProtoConnectionException Unable to achieve ftp/sftp connection, due to socket exception or UnknownHost
     * @throws DataFormatException if Any IOException exceptions caught
     */
    @Override
    public ResponseEntity<Response> connect() {
        String host = this.getProtoEndDto().getUrl();
        int port = 80;
        Map<String, Object> addProperties = this.getProtoEndDto().getAdditionalProperties();
        try{
            if (addProperties != null &&
                    addProperties.containsKey("port")) {
                port = new Integer(String.valueOf(addProperties.get("port")));

            }
        }catch (NumberFormatException e){
            throw new DataFormatException("Invalid port value found!!!, Required format:-Integer");
        }

        HttpMethod method = this.getProtoEndDto().getRequestDetail().getMethod();

        Object uploadFile = this.getProtoEndDto().getRequestDetail().getRequestBody();
        String pathVariable = (addProperties == null || addProperties.get("pathVariable") == null) ? null : String.valueOf(addProperties.get("pathVariable"));
        Response<Object> response = new Response<>();
        HttpStatus status = HttpStatus.OK;
        try {
            establishConnection(host, port);
            switch (method) {
                case GET:
                    ftpClient.changeWorkingDirectory(pathVariable);
                    FTPFile[] ftpFile = ftpClient.listFiles(pathVariable);
                    response.setBody(ftpFile);
                    break;
                case POST:
                    ftpClient.enterLocalPassiveMode();
                    String fileName = (addProperties == null || addProperties.get("fileName") == null) ? RandomString.make() : String.valueOf(addProperties.get("fileName"));
                    boolean isDone = ftpClient.storeFile(fileName, new ByteArrayInputStream(getObjectMapper().writeValueAsBytes(uploadFile)));
                    if (isDone){
                        response.setBody("File successfully uploaded");
                    }else{
                        response.setBody("Unable to upload the file, please try again later.");
                    }
                    break;
                default:
                    throw new ProtoEndException("Invalid method for SFTP connection");
            }
        }catch (IOException e){
            logger.error(e.getMessage(), e);
            throw new DataFormatException(e.getMessage());
        }
        return new ResponseEntity<>(response, status);

    }

    private void establishConnection(String host, int port) throws IOException {
        try {
            ftpClient.connect(host, port);
            ftpClient.login(basicAuth.getUsername(), basicAuth.getPassword());
        } catch (SocketException | UnknownHostException e) {
            logger.error(e.getMessage(), e);
            throw new ProtoConnectionException("Unable to establish the FTP/SFTP connection:" + e.getMessage());
        }
    }
}