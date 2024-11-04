package com.academic.service.Impl;

import com.academic.exception.IdNotFound;
import com.academic.model.Questions;
import com.academic.repository.QuestionRepository;
import com.academic.repository.StudentAptitudeExamRepository;
import com.academic.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService implements IQuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private StudentAptitudeExamRepository aptitudeExamRepository;

    @Override
    public Object sveOrUpdate(Questions questions) {
        if(questionRepository.existsById(questions.getQuestionId())){
            Questions questions1 = questionRepository.findById(questions.getQuestionId()).get();
            if(aptitudeExamRepository.existsById(questions.getAptitudeExamId())){
                questions1.setAptitudeExamId(questions.getAptitudeExamId());
            }else{
                throw new IdNotFound("Aptitude id Not Exist!!!");
            }
            questions1.setQuestion(questions.getQuestion());
            questionRepository.save(questions1);
            return "Successfully Updated!!!";
        }else{
            if(!aptitudeExamRepository.existsById(questions.getAptitudeExamId())){
                throw new IdNotFound("Aptitude id Not Exist!!!");
            }
            questionRepository.save(questions);
            return "Successfully Inserted!!";
        }
    }
}
