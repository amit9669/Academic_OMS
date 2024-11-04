package com.academic.service.Impl;

import com.academic.exception.IdNotFound;
import com.academic.model.StudentAptitudeExam;
import com.academic.repository.CollegeExamRepository;
import com.academic.repository.StudentAptitudeExamRepository;
import com.academic.service.IStudentAptitudeExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentAptitudeExamService implements IStudentAptitudeExamService {

    @Autowired
    private StudentAptitudeExamRepository aptitudeExamRepository;

    @Autowired
    private CollegeExamRepository collegeExamRepository;

    @Override
    public Object saveOrUpdate(StudentAptitudeExam studentAptitudeExam) {

        if(aptitudeExamRepository.existsById(studentAptitudeExam.getAptitudeExamId())){

            StudentAptitudeExam exam = aptitudeExamRepository.findById(studentAptitudeExam.getAptitudeExamId()).get();
            exam.setAptitudeExamId(studentAptitudeExam.getAptitudeExamId());
            if(collegeExamRepository.existsByCollegeExamIdAndIsDeleted(studentAptitudeExam.getCollegeExamId(),false)){
                exam.setCollegeExamId(studentAptitudeExam.getCollegeExamId());
            }else{
                throw new IdNotFound("College Exam Id not exist!! ");
            }
            aptitudeExamRepository.save(exam);
            return "Updated Successfully!!!";
        }else{
            if(!collegeExamRepository.existsByCollegeExamIdAndIsDeleted(studentAptitudeExam.getCollegeExamId(),false)){
                throw new IdNotFound("College Exam Id not exist!! ");
            }
            aptitudeExamRepository.save(studentAptitudeExam);
            return "Successfully Inserted!!!";
        }
    }
}
