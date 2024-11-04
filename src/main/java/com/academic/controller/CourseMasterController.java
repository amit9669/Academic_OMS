package com.academic.controller;

import com.academic.exception.IdNotFound;
import com.academic.model.CourseMaster;
import com.academic.model.response.EntityResponse;
import com.academic.service.ICourseMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courseMaster")
public class CourseMasterController {

    @Autowired
    private ICourseMasterService iCourseMasterService ;

    @PostMapping("/saveOrUpdateCourse")
    public ResponseEntity<?> saveOrUpdateCourse(@RequestBody CourseMaster courseMaster){
        try {
            return new ResponseEntity<>(iCourseMasterService.saveOrUpdateCourse(courseMaster), HttpStatus.OK);
        }catch(IdNotFound e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST) ;
        }
    }

    @GetMapping("/getCourseByID/{courseId}")
    public ResponseEntity<?> getCourseById(@PathVariable Long courseId){
        try{
            return new ResponseEntity<>(iCourseMasterService.getCourseById(courseId),HttpStatus.FOUND) ;
        }catch(IdNotFound e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST) ;
        }
    }

    @GetMapping("/getAllCourse")
    public ResponseEntity<?> getAllCourse(){
        return new ResponseEntity<>(iCourseMasterService.getAllCourse(),HttpStatus.OK) ;
    }

    @DeleteMapping("/softDeleteCourse/{courseId}")
    public ResponseEntity<?> softDeleteCourseByID(@PathVariable Long courseId){
        try{
            return new ResponseEntity<>(iCourseMasterService.softDeleteCourse(courseId),HttpStatus.OK) ;
        }catch(IdNotFound e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST) ;
        }
    }

    @PutMapping("/changeStatus/{courseId}")
    public ResponseEntity<?> changeStatus(@PathVariable Long courseId){
        try {
            return new ResponseEntity<>(iCourseMasterService.changeStatus(courseId),HttpStatus.OK) ;
        }catch(IdNotFound e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST) ;
        }
    }
}
