package com.academic.controller;

import com.academic.exception.IdNotFound;
import com.academic.model.College;
import com.academic.model.request.LogInRequest;
import com.academic.model.request.OTPRequest;
import com.academic.model.response.EntityResponse;
import com.academic.model.response.LogInResponse;
import com.academic.service.ICollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/college")
public class CollegeController {

    @Autowired
    private ICollegeService iCollegeService ;

    @PostMapping("/saveOrUpdateCollege")
    public ResponseEntity<?> saveOrUpdateCollege(@RequestBody College college) throws Exception {
        try {
            return new ResponseEntity<>(iCollegeService.saveOrUpdateCollege(college), HttpStatus.OK);
        }catch (IdNotFound e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST) ;
        }
    }

    @GetMapping("/getCollegeById/{collegeId}")
    public ResponseEntity<?> getCollegeById(@PathVariable Long collegeId) throws Exception {
        try {
            return new ResponseEntity<>(iCollegeService.getCollegeById(collegeId), HttpStatus.FOUND);
        }catch(IdNotFound e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST) ;
        }
    }

    @GetMapping("/getAllCollege")
    public ResponseEntity<?> getAllCollege(){
        return new ResponseEntity<>(iCollegeService.getAllCollege(),HttpStatus.FOUND) ;
    }

    @DeleteMapping("/softDeleteCollegeById/{collegeId}")
    public ResponseEntity<?> softDeleteCollegeById(@PathVariable Long collegeId) throws Exception {
        try {
            return new ResponseEntity<>(iCollegeService.softDeleteCollegeById(collegeId), HttpStatus.OK);
        }catch(IdNotFound e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST) ;
        }
    }

    @PutMapping("/changeCollegeStatus/{collegeId}")
    public ResponseEntity<?> changeStatus(@PathVariable Long collegeId){
        try{
            return new ResponseEntity<>(iCollegeService.changeStatus(collegeId),HttpStatus.OK) ;
        }catch(IdNotFound e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST) ;
        }
    }

    @PostMapping("/logIn")
    public ResponseEntity<?> logIn(@RequestBody LogInRequest logInRequest){
        try{
            return new ResponseEntity<>(iCollegeService.logIn(logInRequest),HttpStatus.OK) ;
        }catch(Exception e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST) ;
        }
    }

    @GetMapping("/checkOTP")
    public ResponseEntity<?> checkOTP(@RequestBody OTPRequest otpRequest){
        try{
            return new ResponseEntity<>(iCollegeService.checkOTP(otpRequest),HttpStatus.OK) ;
        }catch(Exception e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST) ;
        }
    }
}
