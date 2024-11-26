package com.protoend.controller;


import com.protoend.base.util.exceptions.ProtoEndException;
import com.protoend.model.Response;
import com.protoend.model.dto.ProtoEndDto;
import com.protoend.service.ProtoTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/")
public class ProtoEndController {

    private static Logger logger = LoggerFactory.getLogger(ProtoEndController.class);

    @Autowired
    private ProtoTestService protoTestService;

    @PostMapping("/proto/request")
    @SuppressWarnings("unchecked")
    public ResponseEntity<Response> receiverProtoRequest(@RequestBody ProtoEndDto protoTestDto) {
       ResponseEntity<Response> responseEntity = null;
       try {
           responseEntity = protoTestService.testRequest(protoTestDto);
       } catch (ProtoEndException e) {
           logger.error(e.getMessage(), e);
           responseEntity = new ResponseEntity<>(new Response(e.getMessage(), e.getStatus()), HttpStatus.OK);
       } catch (RuntimeException e) {
           logger.error(e.getMessage(), e);
           responseEntity = new ResponseEntity<>(new Response(e.getMessage(), 500), HttpStatus.OK);
       }
        return responseEntity;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProtoEndDto>> getAllProtos(@RequestParam("type") String connectionType) {
        return new ResponseEntity<>(protoTestService.getAll(connectionType), HttpStatus.OK);
    }

}