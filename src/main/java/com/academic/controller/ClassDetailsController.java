package com.academic.controller;

import com.academic.exception.IdNotFound;
import com.academic.model.ClassDetails;
import com.academic.model.response.EntityResponse;
import com.academic.service.IClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/classDetails")
public class ClassDetailsController {

    @Autowired
    private IClassService iClassService;

    @PostMapping("/saveOrUpdateClass")
    public ResponseEntity<?> saveOrUpdateClass(@RequestBody ClassDetails classDetails){
        try{
            return new ResponseEntity<>(iClassService.saveOrUpdateClass(classDetails), HttpStatus.OK) ;
        }catch(IdNotFound e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST) ;
        }
    }

    @GetMapping("/getClassById/{classId}")
    public ResponseEntity<?> getClassByID(@PathVariable Long classId){
        try{
            return new ResponseEntity<>(iClassService.getClassDetailsById(classId), HttpStatus.OK) ;
        }catch(IdNotFound e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST) ;
        }
    }

    @GetMapping("/getAllClass")
    public ResponseEntity<?> getAllClass(){
        return new ResponseEntity<>(iClassService.getAllClassDetails(),HttpStatus.OK) ;
    }

    @GetMapping("/softDelete/{classId}")
    public ResponseEntity<?> softDeleteClassById(@PathVariable Long classId){
        try{
            return new ResponseEntity<>(iClassService.softDeleteClass(classId), HttpStatus.OK) ;
        }catch(IdNotFound e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST) ;
        }
    }

    @PutMapping("/changeStatus/{classId}")
    public ResponseEntity<?> changeStatus(@PathVariable Long classId){
        try{
            return new ResponseEntity<>(iClassService.changeStatus(classId), HttpStatus.OK) ;
        }catch(IdNotFound e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST) ;
        }
    }
}
