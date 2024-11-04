package com.academic.controller;

import com.academic.exception.IdNotFound;
import com.academic.model.CoursePursuingDetails;
import com.academic.model.response.EntityResponse;
import com.academic.service.ICoursePursuingDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coursePursuing")
public class CoursePursuingController {

    @Autowired
    private ICoursePursuingDetailsService iCoursePursuingDetailsService;

    @PostMapping("/saveOrUpdate")
    public ResponseEntity<?> saveOrUpdate(@RequestBody CoursePursuingDetails coursePursuingDetails){
        try{
            return new ResponseEntity<>(iCoursePursuingDetailsService.saveOrUpdate(coursePursuingDetails),HttpStatus.OK);
        }catch(IdNotFound e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
        }
    }
}
