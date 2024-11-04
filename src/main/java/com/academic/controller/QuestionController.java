package com.academic.controller;

import com.academic.exception.IdNotFound;
import com.academic.model.Questions;
import com.academic.model.response.EntityResponse;
import com.academic.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private IQuestionService iQuestionService;

    @PostMapping("/saveOrUpdate")
    public ResponseEntity<?> saveOrUpdate(@RequestBody Questions questions){
        try{
            return new ResponseEntity<>(iQuestionService.sveOrUpdate(questions), HttpStatus.OK);
        }catch(IdNotFound e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
        }
    }
}
