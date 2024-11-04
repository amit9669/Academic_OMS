package com.academic.controller;

import com.academic.exception.IdNotFound;
import com.academic.model.University;
import com.academic.model.response.EntityResponse;
import com.academic.service.IUniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/university")
public class UniversityController {

    @Autowired
    private IUniversityService universityService ;

    @PostMapping("/saveOrUpdateUniversity")
    public ResponseEntity<?> saveOrUpdateUniversity(@RequestBody University university) {
       try {
           return new ResponseEntity<>(universityService.saveOrUpdateUniversity(university), HttpStatus.OK);
       }catch (IdNotFound e){
           return new ResponseEntity<>(new EntityResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST) ;
       }
    }

    @GetMapping("/getUniversity/{universityId}")
    public ResponseEntity<?> getUniversityById(@PathVariable Long universityId) throws Exception {
        try {
            return new ResponseEntity<>(universityService.getUniversityById(universityId), HttpStatus.FOUND);
        }catch(IdNotFound e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST) ;
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllUniversity(){
        return new ResponseEntity<>(universityService.getAllUniversity(),HttpStatus.FOUND) ;
    }

    @DeleteMapping("/softDelete/{universityId}")
    public ResponseEntity<?> softDeleteUniversityByID(@PathVariable Long universityId) throws Exception {
        try {
            return new ResponseEntity<>(universityService.softDeleteUniversityById(universityId), HttpStatus.OK);
        }catch(IdNotFound e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST) ;
        }
    }

    @PutMapping("/changeStatus/{universityId}")
    public ResponseEntity<?> changeStatus(@PathVariable Long universityId){
        try{
            return new ResponseEntity<>(universityService.changeStatus(universityId),HttpStatus.OK) ;
        }catch(IdNotFound e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST) ;
        }
    }

    @GetMapping("/getAllDetails/{universityId}")
    public ResponseEntity<?> getCollegeDetailsAndStudentDetailsUnderUniversity(@PathVariable Long universityId){
        try{
            return new ResponseEntity<>(universityService.getCollegeDetailsAndStudentDetailsUnderUniversity(universityId),HttpStatus.OK) ;
        }catch(IdNotFound e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST) ;
        }
    }
}
