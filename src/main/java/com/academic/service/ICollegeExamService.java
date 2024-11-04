package com.academic.service;

import com.academic.model.CollegeExams;

public interface ICollegeExamService {

    Object saveOrUpdateCollegeExam(CollegeExams collegeExams) ;

    Object getCollegeExamByID(Long collegeExamId) ;

    Object getAllCollegeExams() ;

    Object softDeleteCollegeExam(Long collegeExamId) ;

    Object changeStatus(Long collegeExamId) ;
}
