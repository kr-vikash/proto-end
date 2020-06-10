package com.protoend.controller;


import com.protoend.model.ProtoTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/")
public class ProtoTestController {

    @PostMapping("/discovery")
    public ResponseEntity<HttpEntity<String>> testProtoRequest(@RequestBody ProtoTest protoTest){

        return null;
    }
}
