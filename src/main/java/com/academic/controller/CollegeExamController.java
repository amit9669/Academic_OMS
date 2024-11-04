package com.academic.controller;

import com.academic.exception.IdNotFound;
import com.academic.model.CollegeExams;
import com.academic.model.response.EntityResponse;
import com.academic.service.ICollegeExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/collegeExam")
public class CollegeExamController {

    @Autowired
    private ICollegeExamService iCollegeExamService ;

    @PostMapping("/saveOrUpdateCollegeExam")
    public ResponseEntity<?> saveOrUpdateCollegeExam(@RequestBody CollegeExams collegeExams){
        try{
            return new ResponseEntity<>(iCollegeExamService.saveOrUpdateCollegeExam(collegeExams), HttpStatus.OK) ;
        }catch(IdNotFound e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST) ;
        }
    }

    @GetMapping("/getCollegeExamById/{collegeExamId}")
    public ResponseEntity<?> getCollegeExamById(@PathVariable Long collegeExamId){
        try{
            return new ResponseEntity<>(iCollegeExamService.getCollegeExamByID(collegeExamId), HttpStatus.OK) ;
        }catch(IdNotFound e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST) ;
        }
    }

    @GetMapping("/getAllExams")
    public ResponseEntity<?> getAllCollegeExams(){
        return new ResponseEntity<>(iCollegeExamService.getAllCollegeExams(),HttpStatus.OK) ;
    }

    @DeleteMapping("/softDelete/{collegeExamId}")
    public ResponseEntity<?> softDeleteExamById(@PathVariable Long collegeExamId){
        try{
            return new ResponseEntity<>(iCollegeExamService.softDeleteCollegeExam(collegeExamId), HttpStatus.OK) ;
        }catch(IdNotFound e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST) ;
        }
    }

    @PutMapping("/changeStatus/{collegeExamId}")
    public ResponseEntity<?> changeStatus(@PathVariable Long collegeExamId){
        try{
            return new ResponseEntity<>(iCollegeExamService.changeStatus(collegeExamId), HttpStatus.OK) ;
        }catch(IdNotFound e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST) ;
        }
    }

}
