package com.protoend.service.imp;


import com.protoend.auth.AuthFactory;
import com.protoend.auth.authenticator.Authenticator;
import com.protoend.base.model.enumerator.TestStatus;
import com.protoend.base.util.ProtoEndUtil;
import com.protoend.base.util.exceptions.ProtoEndException;
import com.protoend.base.util.exceptions.RequiredObjectException;
import com.protoend.dao.ProtoTestDAO;
import com.protoend.model.ProtoEnd;
import com.protoend.model.Response;
import com.protoend.model.dto.ProtoEndDto;
import com.protoend.repository.ProtoEndRepository;
import com.protoend.service.ProtoTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.protoend.validator.ConnectorFactory.getConnector;

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
    public ResponseEntity<Response> testRequest(ProtoEndDto protoTestDto) throws ProtoEndException {
        validateRequest(protoTestDto);
        protoTestDto.setStatus(TestStatus.PENDING);
        ProtoEnd protoEnd = null;
        try {
            protoTestDto.setCreatedTime(Instant.now().getEpochSecond());
            protoEnd = protoEndRepository.save(protoTestDto.entityMapper());
            logger.info("ProtoEnd saved to db: " + new ProtoEndDto(protoEnd));
            ResponseEntity<Response> responseEntity = processProtoRequest(protoTestDto);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                protoEnd.setStatus(TestStatus.SUCCESS);
            } else {
                protoEnd.setStatus(TestStatus.FAILED);
            }
            return responseEntity;
        } catch (IOException e) {
            protoTestDto.setStatus(TestStatus.FAILED);
            throw new ProtoEndException("Issue to parse request detail", HttpStatus.BAD_REQUEST.value());
        } finally {
            if (protoEnd != null) {
                protoEndRepository.save(protoEnd);
            }
        }
    }

    @Override
    public List<ProtoEndDto> getAll(String connectionType) {
        List<ProtoEndDto> protoEndDtos = new ArrayList<>();
        List<ProtoEnd> protoEnds = null;
        try {
            protoEnds = protoTestDAO.getAll(connectionType);
            if (protoEnds != null && protoEnds.size() > 0) {
                for (ProtoEnd protoEnd : protoEnds) {
                    protoEndDtos.add(new ProtoEndDto(protoEnd));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ProtoEndException("Unable to fetch data from database", HttpStatus.INTERNAL_SERVER_ERROR.value());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new ProtoEndException("Unable to parse db data to transport object", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return protoEndDtos;
    }

    public ResponseEntity<Response> processProtoRequest(ProtoEndDto protoEndDto) {
        Authenticator authenticator = AuthFactory.getAuthenticator(protoEndDto.getAuthModel(),
                protoEndDto.getRequestDetail().getHeaders(),
                protoEndDto.getRequestDetail().getQueryParameter(),
                protoEndDto.getRequestDetail().getRouteParameter());
        return getConnector(authenticator, protoEndDto).connect();
    }

    private void validateRequest(ProtoEndDto protoEndDto) {
        ProtoEndUtil.notNull(protoEndDto, "ProtoEnd");
        ProtoEndUtil.notNullAndNotEmpty(protoEndDto.getUrl(), "Url");
        ProtoEndUtil.notNull(protoEndDto.getAuthModel(), "Authentication");
    }
}