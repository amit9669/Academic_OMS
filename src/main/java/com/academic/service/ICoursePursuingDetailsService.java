package com.academic.service;

import com.academic.model.CoursePursuingDetails;
import org.springframework.scheduling.annotation.Scheduled;

public interface ICoursePursuingDetailsService {


    public Object saveOrUpdate(CoursePursuingDetails coursePursuingDetails);
}
