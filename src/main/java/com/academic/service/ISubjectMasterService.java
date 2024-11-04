package com.academic.service;

import com.academic.model.SubjectMaster;

public interface ISubjectMasterService {

    public Object saveOrUpdateSubject(SubjectMaster subjectMaster) ;

    public Object getSubjectById(Long subjectId) ;

    public Object getAllSubject() ;

    public Object softDeleteSubjectById(Long subjectId) ;

    public Object changeStatus(Long subjectId) ;
}
