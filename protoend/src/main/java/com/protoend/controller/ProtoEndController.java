package com.protoend.controller;


import com.protoend.model.dto.ProtoEndDto;
import com.protoend.service.ProtoTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/")
public class ProtoEndController {

    @Autowired
        private ProtoTestService protoTestService;

    @PostMapping("/proto/request")
    public ResponseEntity<ProtoEndDto> receiveProtoRequest(@RequestBody ProtoEndDto protoTestDto) {

        ProtoEndDto protoTestDto2 = protoTestService.createProtoTest(protoTestDto);
        return new ResponseEntity<>(protoTestDto2 , HttpStatus.OK);
    }

}