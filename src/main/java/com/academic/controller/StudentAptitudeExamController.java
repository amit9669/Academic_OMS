package com.academic.controller;

import com.academic.exception.IdNotFound;
import com.academic.model.StudentAptitudeExam;
import com.academic.model.response.EntityResponse;
import com.academic.service.IStudentAptitudeExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aptitudeExam")
public class StudentAptitudeExamController {

    @Autowired
    private IStudentAptitudeExamService iAptitudeExamService;

    @PostMapping("/saveOrUpdate")
    public ResponseEntity<?> saveOrUpdate(@RequestBody StudentAptitudeExam studentAptitudeExam){
       try{
           return new ResponseEntity<>(iAptitudeExamService.saveOrUpdate(studentAptitudeExam), HttpStatus.OK);
       }catch(IdNotFound e){
           return new ResponseEntity<>(new EntityResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
       }
    }
}
