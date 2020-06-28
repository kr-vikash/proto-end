package com.protoend.controller;


import com.protoend.model.dto.ProtoEndDto;
import com.protoend.service.ProtoTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/")
public class ProtoEndController {

    @Autowired
    private ProtoTestService protoTestService;

    @PostMapping("/proto/request")
    public ResponseEntity<String> receiverProtoRequest(@RequestBody ProtoEndDto protoTestDto) {

        return protoTestService.testRequest(protoTestDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProtoEndDto>> getAllProtos() {
        return new ResponseEntity<>(protoTestService.getAll(), HttpStatus.OK);
    }

}