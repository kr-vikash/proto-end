package com.protoend.controller;


import com.protoend.model.dto.ProtoTestDto;
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
public class ProtoTestController {

    @Autowired
        private ProtoTestService protoTestService;

    @PostMapping("/connect")
    public ResponseEntity<ProtoTestDto> testProtoRequest(@RequestBody ProtoTestDto protoTestDto) {

        ProtoTestDto protoTestDto2 = protoTestService.createProtoTest(protoTestDto);
        return new ResponseEntity<>(protoTestDto2 , HttpStatus.OK);
    }

}
