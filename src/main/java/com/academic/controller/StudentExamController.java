package com.academic.controller;

import com.academic.exception.IdNotFound;
import com.academic.model.AnswerMaster;
import com.academic.model.StudentExam;
import com.academic.model.StudentExamDetails;
import com.academic.model.response.EntityResponse;
import com.academic.service.IStudentExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/studentExam")
public class StudentExamController {

    @Autowired
    private IStudentExamService iStudentExamService ;

    @PostMapping("/saveOrUpdateStudentExam")
    public ResponseEntity<?> saveOrUpdateStudentExamDetails(@RequestBody StudentExamDetails studentExamDetails){
        try{
            return new ResponseEntity<>(iStudentExamService.saveOrUpdateStudentExam(studentExamDetails),HttpStatus.OK) ;
        }catch(IdNotFound e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST) ;
        }
    }

    @GetMapping("/getStudentExamById/{studentExamId}")
    public ResponseEntity<?> getStudentExamDetailsById(@PathVariable Long studentExamId){
        try{
            return new ResponseEntity<>(iStudentExamService.getStudentExamDetailsById(studentExamId),HttpStatus.FOUND) ;
        }catch(IdNotFound e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST) ;
        }
    }

    @PostMapping("/saveOrUpdateAnswer")
    public ResponseEntity<?> saveOrUpdateAnswer(@RequestBody AnswerMaster answerMaster){
        try{
            return new ResponseEntity<>(iStudentExamService.saveOrUpdateAnswer(answerMaster),HttpStatus.FOUND) ;
        }catch(IdNotFound e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST) ;
        }
    }

    @GetMapping("/getAllAnswer")
    public ResponseEntity<?> getAllAnswer(){
        return new ResponseEntity<>(iStudentExamService.getAllAnswer(),HttpStatus.FOUND) ;
    }

    @PostMapping("/saveOrUpdateStudentAptiExam")
    public ResponseEntity<?> saveOrUpdateStudentAptitudeExam(@RequestBody StudentExam studentExam){
        try{
            return new ResponseEntity<>(iStudentExamService.saveOrUpdateStudentAptitudeExam(studentExam),HttpStatus.OK) ;
        }catch(IdNotFound e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST) ;
        }
    }
}
