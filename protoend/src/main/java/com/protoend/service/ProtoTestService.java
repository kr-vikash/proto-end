package com.protoend.service;

import com.protoend.base.util.exceptions.ProtoEndException;
import com.protoend.model.Response;
import com.protoend.model.dto.ProtoEndDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProtoTestService {

    ResponseEntity<Response> testRequest(ProtoEndDto protoTestDto);

    List<ProtoEndDto> getAll(String connectionType);
}
