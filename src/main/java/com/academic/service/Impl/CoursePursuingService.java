package com.academic.service.Impl;

import com.academic.exception.IdNotFound;
import com.academic.model.CoursePursuingDetails;
import com.academic.repository.CourseMasterRepository;
import com.academic.repository.CoursePursuingRepository;
import com.academic.repository.StudentRepository;
import com.academic.service.ICoursePursuingDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;

@Service
public class CoursePursuingService implements ICoursePursuingDetailsService {

    @Autowired
    private CoursePursuingRepository pursuingRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseMasterRepository courseMasterRepository;

    @Override
    public Object saveOrUpdate(CoursePursuingDetails coursePursuingDetails) {
        if (pursuingRepository.existsById(coursePursuingDetails.getPursuingId())) {
            CoursePursuingDetails coursePursuingDetails1 = pursuingRepository.findById(coursePursuingDetails.getPursuingId()).get();
            if (studentRepository.existsById(coursePursuingDetails.getStudentId())) {
                coursePursuingDetails1.setStudentId(coursePursuingDetails.getStudentId());
            } else {
                throw new IdNotFound("Student Id Not exist!!");
            }

            if (courseMasterRepository.existsById(coursePursuingDetails.getCourseId())) {
                coursePursuingDetails1.setCourseId(coursePursuingDetails.getCourseId());
            } else {
                throw new IdNotFound("Course id Not Exist!!");
            }
            LocalDateTime dateTime = studentRepository.getCreatedAtByStudentId(coursePursuingDetails.getStudentId());
            coursePursuingDetails1.setStartDate(dateTime);

            pursuingRepository.save(coursePursuingDetails1);
            return "Updated Successfully!!!";
        } else {
            if (!studentRepository.existsById(coursePursuingDetails.getStudentId())) {
                throw new IdNotFound("Student Id Not exist!!");
            }
            if (!courseMasterRepository.existsById(coursePursuingDetails.getCourseId())) {
                throw new IdNotFound("Course id Not Exist!!");
            }
            LocalDateTime dateTime = studentRepository.getCreatedAtByStudentId(coursePursuingDetails.getStudentId());
            coursePursuingDetails.setStartDate(dateTime);
            coursePursuingDetails.setCourseYear("FirstYear");
            pursuingRepository.save(coursePursuingDetails);
            return "Successfully inserted!!!";
        }
    }

    @Scheduled(cron = "0 0 12 * * ?")
    public void updateYear(){

        List<CoursePursuingDetails> pursuingDetailsList = pursuingRepository.findAll();
        Iterator<CoursePursuingDetails> detailsIterator = pursuingDetailsList.iterator();

        while(detailsIterator.hasNext()){
            CoursePursuingDetails coursePursuingDetails = detailsIterator.next();

            System.out.println("Today's Date n Time---->"+LocalDateTime.now());
            LocalDateTime dateTime = coursePursuingDetails.getStartDate();

            LocalDate secondYear = LocalDate.of(dateTime.getYear()+1, 5, 1);
            LocalDate thirdYear = LocalDate.of(dateTime.getYear()+2, 5, 1);
            LocalDate fourthYear = LocalDate.of(dateTime.getYear()+3, 5, 1);
            LocalDate endYear = LocalDate.of(dateTime.getYear()+4, 5, 1);

            if (courseMasterRepository.getPeriodByCourseId(coursePursuingDetails.getCourseId()).equals(3)) {

                if ((LocalDate.now().getYear() == secondYear.getYear() && LocalDate.now().getMonthValue() == secondYear.getMonthValue())) {
                    studentRepository.updateTimestampByStudentId(coursePursuingDetails.getStudentId());
                    pursuingRepository.updateCourseYear("SecondYear");
                } else if ((LocalDate.now().getYear() == thirdYear.getYear() && LocalDate.now().getMonthValue() == thirdYear.getMonthValue())) {
                    studentRepository.updateTimestampByStudentId(coursePursuingDetails.getStudentId());
                    pursuingRepository.updateCourseYear("ThirdYear");
                    coursePursuingDetails.setEndDate(fourthYear.atTime(LocalTime.now()));
                }

            } else if (courseMasterRepository.getPeriodByCourseId(coursePursuingDetails.getCourseId()).equals(2)) {

                if ((LocalDate.now().getYear() == secondYear.getYear() && LocalDate.now().getMonthValue() == secondYear.getMonthValue())) {
                    studentRepository.updateTimestampByStudentId(coursePursuingDetails.getStudentId());
                    pursuingRepository.updateCourseYear("SecondYear");
                    coursePursuingDetails.setEndDate(thirdYear.atTime(LocalTime.now()));
                }

            } else if (courseMasterRepository.getPeriodByCourseId(coursePursuingDetails.getCourseId()).equals(4)) {

                if ((LocalDate.now().getYear() == secondYear.getYear() && LocalDate.now().getMonthValue() == secondYear.getMonthValue())) {
                    studentRepository.updateTimestampByStudentId(coursePursuingDetails.getStudentId());
                    pursuingRepository.updateCourseYear("SecondYear");
                } else if ((LocalDate.now().getYear() == thirdYear.getYear() && LocalDate.now().getMonthValue() == thirdYear.getMonthValue())) {
                    studentRepository.updateTimestampByStudentId(coursePursuingDetails.getStudentId());
                    pursuingRepository.updateCourseYear("ThirdYear");
                } else if ((LocalDate.now().getYear() == fourthYear.getYear() && LocalDate.now().getMonthValue() == fourthYear.getMonthValue())) {
                    studentRepository.updateTimestampByStudentId(coursePursuingDetails.getStudentId());
                    pursuingRepository.updateCourseYear("FourthYear");
                    coursePursuingDetails.setEndDate(endYear.atTime(LocalTime.now()));
                }
            }
        }
    }
}
