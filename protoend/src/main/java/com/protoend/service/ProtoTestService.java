package com.protoend.service;

import com.protoend.model.dto.ProtoEndDto;
import org.springframework.http.ResponseEntity;

public interface ProtoTestService {

    ResponseEntity<String> createProtoTest(ProtoEndDto protoTestDto);
}
