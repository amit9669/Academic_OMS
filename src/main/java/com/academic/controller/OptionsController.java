package com.academic.controller;

import com.academic.exception.IdNotFound;
import com.academic.model.request.OptionsRequest;
import com.academic.model.response.EntityResponse;
import com.academic.service.IOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/option")
public class OptionsController {

    @Autowired
    private IOptionService iOptionService;

    @PostMapping("/saveOrUpdate")
    public ResponseEntity<?> saveOrUpdate(@RequestBody List<OptionsRequest> optionsRequest){
        try{
            return new ResponseEntity<>(iOptionService.saveOrUpdate(optionsRequest), HttpStatus.OK);
        }catch(IdNotFound e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
        }
    }
}
