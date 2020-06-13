package com.protoend.service.imp;


import com.protoend.model.ProtoTest;
import com.protoend.model.dto.ProtoTestDto;
import com.protoend.repository.ProtoEndRepository;
import com.protoend.service.ProtoTestService;
import com.protoend.util.exceptions.ProtoEndException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ProtoTestServiceImp implements ProtoTestService {

    @Autowired
    private ProtoEndRepository protoEndRepository;

    @Override
    public ProtoTestDto createProtoTest(ProtoTestDto protoTestDto) {
        ProtoTest protoTest = protoEndRepository.save(protoTestDto.entityMapper());
        try {
            return new ProtoTestDto(protoTest);
        } catch (IOException e) {
            throw new ProtoEndException("Issue to parse request detail");
        }
    }
}
