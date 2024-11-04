package com.academic.service.Impl;

import com.academic.exception.IdNotFound;
import com.academic.model.CollegeExams;
import com.academic.repository.ClassDetailsRepository;
import com.academic.repository.CollegeExamRepository;
import com.academic.repository.CollegeRepository;
import com.academic.repository.SubjectRepository;
import com.academic.service.ICollegeExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollegeExamService implements ICollegeExamService {

    @Autowired
    private CollegeExamRepository collegeExamRepository;

    @Autowired
    private CollegeRepository collegeRepository ;

    @Autowired
    private ClassDetailsRepository classDetailsRepository ;

    @Autowired
    private SubjectRepository subjectRepository ;

    @Override
    public Object saveOrUpdateCollegeExam(CollegeExams collegeExams) {
        if(collegeExamRepository.existsById(collegeExams.getCollegeExamId())){
            CollegeExams collegeExams1 = collegeExamRepository.findById(collegeExams.getCollegeExamId()).get() ;
            if(collegeRepository.existsByCollegeIdAndIsDeleted(collegeExams.getCollegeId(),false)){
                collegeExams1.setCollegeId(collegeExams.getCollegeId());
            }else{
                throw new IdNotFound("College Id Not Exist!!") ;
            }
            if(subjectRepository.existsBySubjectIdAndIsDeleted(collegeExams.getSubjectId(),false)){
                collegeExams1.setSubjectId(collegeExams.getSubjectId());
            }else{
                throw new IdNotFound("Subject Id Not Exist!!") ;
            }
            if(classDetailsRepository.existsByClassIdAndIsDeleted(collegeExams.getClassId(),false)){
                collegeExams1.setClassId(collegeExams.getClassId());
            }else{
                throw new IdNotFound("Class Id Not Exist!!") ;
            }
            collegeExams1.setExamStartDate(collegeExams.getExamStartDate());
            collegeExams1.setExamStartTime(collegeExams.getExamStartTime());
            collegeExams1.setExamEndTime(collegeExams.getExamEndTime());
            collegeExams1.setPassingMarks(collegeExams.getPassingMarks());
            collegeExams1.setExamPattern(collegeExams.getExamPattern());
            collegeExamRepository.save(collegeExams1) ;
            return "Updated Successfully!!!" ;
        }else{
            if(!collegeRepository.existsByCollegeIdAndIsDeleted(collegeExams.getCollegeId(),false)){
                throw new IdNotFound("College Id Not Exist!!") ;
            }
            if(!subjectRepository.existsBySubjectIdAndIsDeleted(collegeExams.getSubjectId(),false)){
                throw new IdNotFound("Subject Id Not Exist!!") ;
            }
            if(!classDetailsRepository.existsByClassIdAndIsDeleted(collegeExams.getClassId(),false)){
                throw new IdNotFound("Class Id Not Exist!!") ;
            }
            collegeExamRepository.save(collegeExams) ;
            return "Inserted Successfully!!" ;
        }
    }

    @Override
    public Object getCollegeExamByID(Long collegeExamId) {
        if(collegeExamRepository.existsById(collegeExamId)){
            return collegeExamRepository.findById(collegeExamId) ;
        }else{
            throw new IdNotFound("No Exam!!") ;
        }
    }

    @Override
    public Object getAllCollegeExams() {
        return collegeExamRepository.findAll();
    }

    @Override
    public Object softDeleteCollegeExam(Long collegeExamId) {
        if(collegeExamRepository.existsById(collegeExamId)){
            CollegeExams collegeExams = collegeExamRepository.findById(collegeExamId).get() ;
            if(collegeExams.getIsDeleted()){
                collegeExams.setIsDeleted(true);
            }
            collegeExamRepository.save(collegeExams) ;
            return "Deleted Successfully!!!" ;
        }else{
            throw new IdNotFound("No Exam!!") ;
        }
    }

    @Override
    public Object changeStatus(Long collegeExamId) {
        if(collegeExamRepository.existsById(collegeExamId)){
            CollegeExams collegeExams = collegeExamRepository.findById(collegeExamId).get() ;
            if(collegeExams.isActive()){
                collegeExams.setActive(false);
            }else{
                collegeExams.setActive(true);
            }
            collegeExamRepository.save(collegeExams) ;
            return "Change College Exam Status!!" ;
        }else{
            throw new IdNotFound("No Exam!!") ;
        }
    }
}
