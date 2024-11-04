package com.academic.service;

import com.academic.model.ClassDetails;

public interface IClassService {

    public Object saveOrUpdateClass(ClassDetails classDetails) ;

    public Object getClassDetailsById(Long classId) ;

    public Object getAllClassDetails() ;

    public Object softDeleteClass(Long classId) ;

    public Object changeStatus(Long classId) ;
}
