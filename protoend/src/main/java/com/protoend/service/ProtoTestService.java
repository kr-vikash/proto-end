package com.protoend.service;

import com.protoend.model.dto.ProtoEndDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProtoTestService {

    ResponseEntity<String> createProtoTest(ProtoEndDto protoTestDto);

    List<ProtoEndDto> getAll();
}
