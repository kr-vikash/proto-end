package com.protoend.service.imp;


import com.protoend.auth.AuthFactory;
import com.protoend.auth.authenticator.Authenticator;
import com.protoend.base.model.enumerator.TestStatus;
import com.protoend.base.util.Constants;
import com.protoend.base.util.exceptions.ProtoEndException;
import com.protoend.dao.ProtoTestDAO;
import com.protoend.model.ProtoEnd;
import com.protoend.model.dto.ProtoEndDto;
import com.protoend.repository.ProtoEndRepository;
import com.protoend.service.ProtoTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProtoTestServiceImp implements ProtoTestService {

    private static Logger logger = LoggerFactory.getLogger(ProtoTestServiceImp.class);

    @Autowired
    private ProtoEndRepository protoEndRepository;

    @Autowired
    private ProtoTestDAO protoTestDAO;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ResponseEntity<String> createProtoTest(ProtoEndDto protoTestDto) {
        protoTestDto.setStatus(TestStatus.PENDING);
        try {
            ResponseEntity<String> responseEntity = sendProtoRequest(protoTestDto);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                protoTestDto.setStatus(TestStatus.SUCCESS);
            } else {
                protoTestDto.setStatus(TestStatus.FAILED);
            }
            ProtoEnd protoEnd = protoEndRepository.save(protoTestDto.entityMapper());
            logger.info("ProtoEnd saved to db: " + new ProtoEndDto(protoEnd));
            return responseEntity;
        } catch (IOException e) {
            throw new ProtoEndException("Issue to parse request detail");
        }
    }

    @Override
    public List<ProtoEndDto> getAll() {
        List<ProtoEndDto> protoEndDtos = new ArrayList<>();
        List<ProtoEnd> protoEnds = null;
        try {
            protoEnds = protoTestDAO.getAll();
            if (protoEnds!=null && protoEnds.size()> 0) {
                for (ProtoEnd protoEnd : protoEnds){
                    protoEndDtos.add(new ProtoEndDto(protoEnd));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ProtoEndException("Unable to fetch data from database");
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new ProtoEndException("Unable to parse db data to transport object");
        }

        return protoEndDtos;
    }

    public ResponseEntity<String> sendProtoRequest(ProtoEndDto protoEndDto) {

        Authenticator authenticator = AuthFactory.getAuthenticator(protoEndDto.getAuthModel(),
                protoEndDto.getRequestDetail().getHeaders(),
                protoEndDto.getRequestDetail().getQueryParameter());
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.setAll(authenticator.getHeaders() != null ? authenticator.getHeaders() : new HashMap<>());
        ResponseEntity<String> responseEntity = null;
        switch (protoEndDto.getConnectionType()) {
            case SOAP:
                HttpEntity<Object> request = new HttpEntity<>(protoEndDto.getRequestDetail().getRequestBody(), headers);
                responseEntity = restTemplate.exchange(protoEndDto.getUrl(), protoEndDto.getRequestDetail().getMethod(), request, String.class);
                break;
            case REST:
                request = new HttpEntity<>(protoEndDto.getRequestDetail().getRequestBody(), headers);
                responseEntity = restTemplate.exchange(protoEndDto.getUrl(), protoEndDto.getRequestDetail().getMethod(), request, String.class);
                break;
            case FTP:
                break;
            case SFTP:
                break;
        }
        return responseEntity;
    }

    public void parseQueryParamToUrl(ProtoEndDto protoEndDto) {
        if (protoEndDto.getRequestDetail().getQueryParameter() == null || protoEndDto.getRequestDetail().getQueryParameter().isEmpty()) {
            return;
        }
        String url = protoEndDto.getUrl();
        Map<String, String> queryParam = protoEndDto.getRequestDetail().getQueryParameter();
        StringBuilder urlBuilder = new StringBuilder(url);
        String prefix = "";
        for (Map.Entry<String, String> entry : queryParam.entrySet()) {
            urlBuilder.append(prefix).append(entry.getKey()).append(Constants.EQUAL).append(entry.getValue()).append(Constants.COMMA);
            prefix = Constants.COMMA;
        }
        protoEndDto.setUrl(urlBuilder.toString());
    }
}