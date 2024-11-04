package com.academic.service;

import com.academic.model.AnswerMaster;
import com.academic.model.StudentExam;
import com.academic.model.StudentExamDetails;

public interface IStudentExamService {

    public Object saveOrUpdateStudentExam(StudentExamDetails studentExamDetails) ;

    public Object getStudentExamDetailsById(Long studentExamId) ;

    public Object saveOrUpdateAnswer(AnswerMaster answerMaster);

    public Object getAllAnswer();

    public Object softDeleteAnswer(Long answerId);

    public Object saveOrUpdateStudentAptitudeExam(StudentExam studentExam);

    public int checkCorrectOrWrong(Long studentId);

}
