package com.academic.service;

import com.academic.model.University;

public interface IUniversityService {

   public Object saveOrUpdateUniversity(University university);

    public Object getUniversityById(Long universityId) throws Exception;

    public Object getAllUniversity() ;

    public Object softDeleteUniversityById(Long universityId) throws Exception;

    public Object changeStatus(Long universityID) ;

    public Object getCollegeDetailsAndStudentDetailsUnderUniversity(Long universityId);
}
