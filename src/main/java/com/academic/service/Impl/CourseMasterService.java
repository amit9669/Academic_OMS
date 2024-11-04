package com.academic.service.Impl;

import com.academic.exception.IdNotFound;
import com.academic.model.CourseMaster;
import com.academic.repository.CourseMasterRepository;
import com.academic.service.ICourseMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseMasterService implements ICourseMasterService {

    @Autowired
    private CourseMasterRepository courseMasterRepository ;

    @Override
    public Object saveOrUpdateCourse(CourseMaster courseMaster) {
        if(courseMasterRepository.existsById(courseMaster.getCourseId())){
            List<Long> couIds = new ArrayList<>() ;
            couIds.add(courseMaster.getCourseId()) ;
            CourseMaster courseMaster1 = courseMasterRepository.findById(courseMaster.getCourseId()).get() ;
            if(courseMasterRepository.existsByCourseNameAndCourseIdNotIn(courseMaster.getCourseName(),couIds)){
                throw new IdNotFound("Course Already Exist!!!") ;
            }else {
                courseMaster1.setCourseId(courseMaster.getCourseId());
                courseMaster1.setPeriod(courseMaster.getPeriod());
                courseMaster1.setCourseName(courseMaster.getCourseName());
                courseMaster1.setCourseLanguage(courseMaster.getCourseLanguage());
                courseMasterRepository.save(courseMaster1);
                return "Updated Successfully!!!";
            }
        }else{
            if(courseMasterRepository.existsByCourseName(courseMaster.getCourseName())){
                throw new IdNotFound("Course Already Exist!!!") ;
            }else {
                courseMasterRepository.save(courseMaster);
                return "Inserted Successfully!!!";
            }
        }
    }

    @Override
    public Object getCourseById(Long courseId) {
        if(courseMasterRepository.existsById(courseId)){
            return courseMasterRepository.findById(courseId) ;
        }else{
            throw new IdNotFound("Id Not Exist!!!") ;
        }
    }

    @Override
    public Object getAllCourse() {
        return courseMasterRepository.findAll();
    }

    @Override
    public Object softDeleteCourse(Long courseId) {
        if(courseMasterRepository.existsById(courseId)){
            CourseMaster courseMaster = courseMasterRepository.findById(courseId).get() ;
            courseMaster.setIsDeleted(true);
            courseMasterRepository.save(courseMaster) ;
            return "Delete Successfully!!!";
        }else{
           throw new IdNotFound("Id Not Exist!!!");
        }
    }

    @Override
    public Object changeStatus(Long courseId) {
        if(courseMasterRepository.existsById(courseId)){
            CourseMaster courseMaster = courseMasterRepository.findById(courseId).get();
            if(courseMaster.isActive()){
                courseMaster.setActive(false);
            }else{
                courseMaster.setActive(true);
            }
            courseMasterRepository.save(courseMaster) ;
            return "Change Course Active Status!!!" ;
        }else{
            throw new IdNotFound("Course Not Exist!!!") ;
        }
    }
}
