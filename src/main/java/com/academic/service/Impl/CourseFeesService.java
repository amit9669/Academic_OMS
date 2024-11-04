package com.academic.service.Impl;

import com.academic.exception.IdNotFound;
import com.academic.model.CourseFeesDetails;
import com.academic.repository.CourseFeeRepository;
import com.academic.repository.CourseMasterRepository;
import com.academic.service.ICourseFeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseFeesService implements ICourseFeesService {

    @Autowired
    private CourseFeeRepository courseFeeRepository;

    @Autowired
    private CourseMasterRepository courseMasterRepository;

    @Override
    public Object saveOrUpdate(CourseFeesDetails courseFeesDetails) {
        if (courseFeeRepository.existsById(courseFeesDetails.getFeesId())) {

            CourseFeesDetails courseFeesDetails1 = courseFeeRepository.findById(courseFeesDetails.getFeesId()).get();
            if (!courseMasterRepository.existsById(courseFeesDetails.getCourseId())) {
                courseFeesDetails1.setFeesId(courseFeesDetails.getFeesId());
                throw new IdNotFound("Course Not Exist!!");
            }else{
                courseFeesDetails1.setCourseId(courseFeesDetails.getCourseId());
            }
            courseFeesDetails1.setYear(courseFeesDetails.getYear());
            courseFeesDetails1.setFeesAmount(courseFeesDetails.getFeesAmount());
            courseFeeRepository.save(courseFeesDetails1);
            return "Updated Successfully!!!";
        } else {
            if (!courseMasterRepository.existsById(courseFeesDetails.getCourseId())) {
                throw new IdNotFound("Course Not Exist!!");
            }
            courseFeeRepository.save(courseFeesDetails);
            return "Inserted Successfully!!!";
        }
    }
}
