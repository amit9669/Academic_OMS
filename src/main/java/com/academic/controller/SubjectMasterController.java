package com.academic.controller;

import com.academic.exception.IdNotFound;
import com.academic.model.SubjectMaster;
import com.academic.model.response.EntityResponse;
import com.academic.service.ISubjectMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subject")
public class SubjectMasterController {

    @Autowired
    private ISubjectMasterService iSubjectMasterService ;

    @PostMapping("/saveOrUpdateSubject")
    public ResponseEntity<?> saveOrUpdateSubject(@RequestBody SubjectMaster subjectMaster){
        try{
            return new ResponseEntity<>(iSubjectMasterService.saveOrUpdateSubject(subjectMaster),HttpStatus.OK) ;
        }catch(IdNotFound e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST) ;
        }
    }

    @GetMapping("/getSubjectByID/{subjectId}")
    public ResponseEntity<?> getSubjectById(@PathVariable Long subjectId){
        try{
            return new ResponseEntity<>(iSubjectMasterService.getSubjectById(subjectId),HttpStatus.OK) ;
        }catch(IdNotFound e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST) ;
        }
    }

    @GetMapping("/getAllSubject")
    public ResponseEntity<?> getAllSubject(){
        return new ResponseEntity<>(iSubjectMasterService.getAllSubject(),HttpStatus.OK) ;
    }

    @GetMapping("/softDelete/{subjectId}")
    public ResponseEntity<?> softDeleteBySubjectId(@PathVariable Long subjectId){
        try{
            return new ResponseEntity<>(iSubjectMasterService.softDeleteSubjectById(subjectId),HttpStatus.OK) ;
        }catch(IdNotFound e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST) ;
        }
    }

    @PutMapping("/changeStatus/{subjectId}")
    public ResponseEntity<?> changeStatus(@PathVariable Long subjectId){
        try{
            return new ResponseEntity<>(iSubjectMasterService.changeStatus(subjectId),HttpStatus.OK) ;
        }catch(IdNotFound e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST) ;
        }
    }
}
