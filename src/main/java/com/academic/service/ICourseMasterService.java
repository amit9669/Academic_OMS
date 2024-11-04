package com.academic.service;

import com.academic.model.CourseMaster;

public interface ICourseMasterService {

    public Object saveOrUpdateCourse(CourseMaster courseMaster) ;

    public Object getCourseById(Long courseId) ;

    public Object getAllCourse() ;

    public Object softDeleteCourse(Long courseId) ;

    public Object changeStatus(Long courseId) ;
}
