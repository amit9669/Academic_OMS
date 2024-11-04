package com.academic.service;

import com.academic.model.College;
import com.academic.model.request.LogInRequest;
import com.academic.model.request.OTPRequest;

public interface ICollegeService {

    Object saveOrUpdateCollege(College college) throws Exception;

    Object getCollegeById(Long collegeId) throws Exception;

    Object getAllCollege() ;

    Object softDeleteCollegeById(Long collegeId) throws Exception;

    Object changeStatus(Long collegeId) ;

    Object logIn(LogInRequest logInRequest) ;

    public Integer generateOTP();

    public void sendEmail(String to, String subject,String body);

    Object checkOTP(OTPRequest otpRequest);
}
