package com.protoend.service.imp;


import com.protoend.base.util.ProtoEndUtil;
import com.protoend.base.util.exceptions.ProtoEndException;
import com.protoend.config.RestClientConfig;
import com.protoend.model.ProtoEnd;
import com.protoend.model.dto.ProtoEndDto;
import com.protoend.repository.ProtoEndRepository;
import com.protoend.service.ProtoTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class ProtoTestServiceImp implements ProtoTestService {

    @Autowired
    private ProtoEndRepository protoEndRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ProtoEndDto createProtoTest(ProtoEndDto protoTestDto) {
        ProtoEnd protoEnd = protoEndRepository.save(protoTestDto.entityMapper());
        try {
            return new ProtoEndDto(protoEnd);
        } catch (IOException e) {
            throw new ProtoEndException("Issue to parse request detail");
        }
    }

//    public void sendProtoRequest(ProtoEndDto protoEndDto){
//
//        switch (protoEndDto.getConnectionType()){
//
//            case SOAP:
//                HttpEntity<String> request = new HttpEntity<String>(protoEndDto.getRequestDetail().getRequestBody());
//                restTemplate.exchange(protoEndDto.getUrl(),)
//                break;
//            case REST:
//                break;
//            case FTP:
//                break;
//            case SFTP:
//                break;
//        }
//    }
}