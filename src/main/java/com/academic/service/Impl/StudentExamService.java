package com.academic.service.Impl;

import com.academic.exception.IdNotFound;
import com.academic.model.AnswerMaster;
import com.academic.model.Options;
import com.academic.model.StudentExam;
import com.academic.model.StudentExamDetails;
import com.academic.repository.*;
import com.academic.service.IStudentExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class StudentExamService implements IStudentExamService {

    @Autowired
    private StudentExamRepository studentExamRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CollegeExamRepository collegeExamRepository;

    @Autowired
    private StudentAptiExamRepository studentAptiExamRepository;

    @Autowired
    private AnswerMasterRepository answerMasterRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private OptionsRepository optionsRepository;


    @Override
    public Object saveOrUpdateStudentExam(StudentExamDetails studentExamDetails) {
        if (studentExamRepository.existsById(studentExamDetails.getStudentExamId())) {
            StudentExamDetails studentExamDetails1 = studentExamRepository.findById(studentExamDetails.getStudentExamId()).get();
            if (studentRepository.existsByStudentIdAndIsDeleted(studentExamDetails.getStudentId(), false)) {
                studentExamDetails1.setStudentId(studentExamDetails.getStudentId());
            } else {
                throw new IdNotFound("Student id Not Exist!!");
            }
            if (collegeExamRepository.existsByCollegeExamIdAndIsDeleted(studentExamDetails.getCollegeExamId(), false)) {
                studentExamDetails1.setCollegeExamId(studentExamDetails.getCollegeExamId());
            } else {
                throw new IdNotFound("College Exam id Not Exist!!");
            }
            int marks = this.checkCorrectOrWrong(studentExamDetails.getStudentId());
            studentExamDetails.setMarksObtained(marks);
            studentExamDetails1.setOutOfMarks(studentExamDetails.getOutOfMarks());
            studentExamRepository.save(studentExamDetails1);
            return "Updated Successfully";
        } else {
            if (!studentRepository.existsByStudentIdAndIsDeleted(studentExamDetails.getStudentId(), false)) {
                throw new IdNotFound("Student ID not Exist!!");
            }
            if (!collegeExamRepository.existsByCollegeExamIdAndIsDeleted(studentExamDetails.getCollegeExamId(), false)) {
                throw new IdNotFound("College ID Not Exist!!");
            }
            int marks = this.checkCorrectOrWrong(studentExamDetails.getStudentId());
            studentExamDetails.setMarksObtained(marks);
            studentExamRepository.save(studentExamDetails);
            return "Inserted Successfully";
        }
    }

    @Override
    public int checkCorrectOrWrong(Long studentId) {
        int marks = 0;
        List<StudentExam> studentExam = studentAptiExamRepository.getStudentById(studentId);

        for (int i = 0; i < studentExam.size(); i++) {
            AnswerMaster answerMaster = answerMasterRepository.getQuestionByQuestionId(studentExam.get(i).getQuestionId());

            Long answer = answerMaster.getAnswer(); //2 , 6, 12

            if (studentExam.get(i).getOption().equals(answer)) {
                marks++;
            }
        }
        return marks;
    }

    @Override
    public Object getStudentExamDetailsById(Long studentExamId) {
        if (studentExamRepository.existsById(studentExamId)) {
            return studentExamRepository.findById(studentExamId);
        } else {
            throw new IdNotFound("No Id Found!!!");
        }
    }

    @Override
    public Object saveOrUpdateAnswer(AnswerMaster answerMaster) {
        if (answerMasterRepository.existsById(answerMaster.getAnswerId())) {
            AnswerMaster answerMaster1 = answerMasterRepository.findById(answerMaster.getAnswerId()).get();
            if (collegeExamRepository.existsByCollegeExamIdAndIsDeleted(answerMaster.getCollegeExamId(), false)) {
                answerMaster1.setCollegeExamId(answerMaster.getCollegeExamId());
            } else {
                throw new IdNotFound("college exam id not found");
            }
            if (questionRepository.existsById(answerMaster.getQuestionId())) {
                answerMaster1.setQuestionId(answerMaster.getQuestionId());
            } else {
                throw new IdNotFound("Question id not exist!!");
            }
            List<Options> optionsOfAnswer = optionsRepository.getAllDataByQuestionId(answerMaster1.getQuestionId());
            for (int i = 0; i < optionsOfAnswer.size(); i++) {
                if ((i + 1) == answerMaster.getAnswer()) {
                    answerMaster1.setAnswer(optionsOfAnswer.get(i).getOptionId());
                }
            }
            answerMasterRepository.save(answerMaster1);
            return "Updated Successfully!!";
        } else {
            if (!collegeExamRepository.existsByCollegeExamIdAndIsDeleted(answerMaster.getCollegeExamId(), false)) {
                throw new IdNotFound("college exam id not found");
            }
            if (!questionRepository.existsById(answerMaster.getQuestionId())) {
                throw new IdNotFound("Question id not exist!!");
            }
            List<Options> optionsOfAnswer = optionsRepository.getAllDataByQuestionId(answerMaster.getQuestionId());

            for (int i = 0; i < optionsOfAnswer.size(); i++) {
                if ((i + 1) == answerMaster.getAnswer()) {
                    answerMaster.setAnswer(optionsOfAnswer.get(i).getOptionId());
                }
            }
            answerMasterRepository.save(answerMaster);
            return "Inserted Successfully!!";
        }
    }

    @Override
    public Object getAllAnswer() {
        return answerMasterRepository.findAll();
    }

    @Override
    public Object softDeleteAnswer(Long answerId) {
        if (answerMasterRepository.existsById(answerId)) {
            AnswerMaster answerMaster = answerMasterRepository.findById(answerId).get();
            if (answerMaster.getIsDeleted()) {
                answerMaster.setIsDeleted(true);
            }
            answerMasterRepository.save(answerMaster);
            return "Deleted Data!!";
        } else {
            throw new IdNotFound("Answer Id Not Exist!!!");
        }
    }

    @Override
    public Object saveOrUpdateStudentAptitudeExam(StudentExam studentExam) {
        if (studentAptiExamRepository.existsById(studentExam.getStudentExamId())) {
            StudentExam studentExam1 = studentAptiExamRepository.findById(studentExam.getStudentExamId()).get();
            if (studentRepository.existsById(studentExam.getStudentId())) {
                studentExam1.setStudentId(studentExam.getStudentId());
            } else {
                throw new IdNotFound("Student not exist");
            }
            if (collegeExamRepository.existsByCollegeExamIdAndIsDeleted(studentExam.getCollegeExamId(), false)) {
                studentExam1.setCollegeExamId(studentExam.getCollegeExamId());
            } else {
                throw new IdNotFound("college exam id not found");
            }
            if (questionRepository.existsById(studentExam.getQuestionId())) {
                studentExam1.setQuestionId(studentExam.getQuestionId());
            } else {
                throw new IdNotFound("Question not exist!!");
            }
            if (optionsRepository.existsById(studentExam.getOption())) {
                List<Options> optionsOfAnswer = optionsRepository.getAllDataByQuestionId(studentExam.getQuestionId());
                for (int i = 0; i < optionsOfAnswer.size(); i++) {
                    if ((i + 1) == studentExam.getOption()) {
                        studentExam1.setOption(optionsOfAnswer.get(i).getOptionId());
                    }
                }
            } else {
                throw new IdNotFound("Option not exist!!!");
            }
            studentAptiExamRepository.save(studentExam1);
            return "Updated Successfully!!";
        } else {
            if (!studentRepository.existsById(studentExam.getStudentId())) {
                throw new IdNotFound("Student not exist");
            }
            if (!collegeExamRepository.existsByCollegeExamIdAndIsDeleted(studentExam.getCollegeExamId(), false)) {
                throw new IdNotFound("college exam id not found");
            }
            if (!questionRepository.existsById(studentExam.getQuestionId())) {
                throw new IdNotFound("Question not exist!!");
            }
            if (!optionsRepository.existsById(studentExam.getOption())) {
                throw new IdNotFound("Option not exist!!!");
            } else {
                List<Options> optionsOfAnswer = optionsRepository.getAllDataByQuestionId(studentExam.getQuestionId());

                for (int i = 0; i < optionsOfAnswer.size(); i++) {
                    if ((i + 1) == studentExam.getOption()) {
                        studentExam.setOption(optionsOfAnswer.get(i).getOptionId());
                    }
                }
            }
            studentAptiExamRepository.save(studentExam);
            return "Inserted Successfully!!";
        }
    }
}
