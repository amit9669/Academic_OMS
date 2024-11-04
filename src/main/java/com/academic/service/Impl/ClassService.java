package com.academic.service.Impl;

import com.academic.exception.IdNotFound;
import com.academic.model.ClassDetails;
import com.academic.repository.ClassDetailsRepository;
import com.academic.repository.CourseMasterRepository;
import com.academic.repository.StreamMasterRepository;
import com.academic.repository.StudentRepository;
import com.academic.service.IClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClassService implements IClassService {

    @Autowired
    private ClassDetailsRepository classDetailsRepository;

    @Autowired
    private CourseMasterRepository courseMasterRepository;

    @Autowired
    private StreamMasterRepository streamMasterRepository;


    @Override
    public Object saveOrUpdateClass(ClassDetails classDetails) {
        if (classDetailsRepository.existsById(classDetails.getClassId())) {
            ClassDetails classDetails1 = classDetailsRepository.findById(classDetails.getClassId()).get();
            List<Long> classIds=new ArrayList<>();
            classIds.add(classDetails.getClassId());
            if (!classDetailsRepository.existsByCourseIdAndStreamIdAndDivisionAndClassIdNotIn(classDetails.getCourseId(), classDetails.getStreamId(), classDetails.getDivision(),classIds)) {

                classDetails1.setDivision(classDetails.getDivision());

                if (streamMasterRepository.existsByStreamIdAndIsDeleted(classDetails.getStreamId(), false)) {
                    classDetails1.setStreamId(classDetails.getStreamId());
                } else {
                    throw new IdNotFound("Stream Id Not Exist!!");
                }
                if (courseMasterRepository.existsByCourseIdAndIsDeleted(classDetails.getCourseId(), false)) {
                    classDetails1.setCourseId(classDetails.getCourseId());
                } else {
                    throw new IdNotFound("Course Id Not Exist!!");
                }
            } else {
                throw new IdNotFound("Course And Stream Already Exist!!");
            }

            classDetails1.setTotalStudentsCount(classDetails.getTotalStudentsCount());
            classDetailsRepository.save(classDetails1);
            return "Updated Successfully!!!";
        } else {
            if (!classDetailsRepository.existsByCourseIdAndStreamIdAndDivision(classDetails.getCourseId(), classDetails.getStreamId(), classDetails.getDivision())) {
                if (!courseMasterRepository.existsByCourseIdAndIsDeleted(classDetails.getCourseId(), false)) {
                    throw new IdNotFound("Course Id Not Exist!!");
                } else if (!streamMasterRepository.existsByStreamIdAndIsDeleted(classDetails.getStreamId(), false)) {
                    throw new IdNotFound("Stream Id Not Exist!!");
                } else {
                    classDetailsRepository.save(classDetails);
                    return "Inserted Successfully!!!";
                }
            } else {
                throw new IdNotFound("That Field Already Exist!!!");
            }
        }
    }

    @Override
    public Object getClassDetailsById(Long classId) {
        if (classDetailsRepository.existsById(classId)) {
            return classDetailsRepository.findById(classId).get();
        } else {
            throw new IdNotFound("Class Id Not Exist!!!");
        }
    }

    @Override
    public Object getAllClassDetails() {
        return classDetailsRepository.findAll();
    }

    @Override
    public Object softDeleteClass(Long classId) {
        if (classDetailsRepository.existsById(classId)) {
            ClassDetails classDetails = classDetailsRepository.findById(classId).get();
            if (classDetails.getIsDeleted()) {
                classDetails.setIsDeleted(false);
            }
            classDetailsRepository.save(classDetails);
            return "Deleted Successfully!!!";
        } else {
            throw new IdNotFound("Class Id Not Exist!!!");
        }
    }

    @Override
    public Object changeStatus(Long classId) {
        if (classDetailsRepository.existsById(classId)) {
            ClassDetails classDetails = classDetailsRepository.findById(classId).get();
            if (classDetails.isActive()) {
                classDetails.setActive(false);
            } else {
                classDetails.setActive(true);
            }
            classDetailsRepository.save(classDetails);
            return "Change Class Active Status!!";
        } else {
            throw new IdNotFound("Class Id Not Exist!!!");
        }
    }
}
