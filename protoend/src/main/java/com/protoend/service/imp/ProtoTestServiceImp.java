package com.protoend.service.imp;


import com.protoend.base.util.exceptions.ProtoEndException;
import com.protoend.model.ProtoEnd;
import com.protoend.model.dto.ProtoEndDto;
import com.protoend.repository.ProtoEndRepository;
import com.protoend.service.ProtoTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ProtoTestServiceImp implements ProtoTestService {

    @Autowired
    private ProtoEndRepository protoEndRepository;

    @Override
    public ProtoEndDto createProtoTest(ProtoEndDto protoTestDto) {
        ProtoEnd protoEnd = protoEndRepository.save(protoTestDto.entityMapper());
        try {
            return new ProtoEndDto(protoEnd);
        } catch (IOException e) {
            throw new ProtoEndException("Issue to parse request detail");
        }
    }
}
