package com.academic.service.Impl;

import com.academic.exception.IdNotFound;
import com.academic.model.College;
import com.academic.model.Student;
import com.academic.model.University;
import com.academic.repository.CollegeRepository;
import com.academic.repository.StudentRepository;
import com.academic.repository.UniversityRepository;
import com.academic.service.IUniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UniversityService implements IUniversityService {

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private CollegeRepository collegeRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Object saveOrUpdateUniversity(University university) {

        if (universityRepository.existsById(university.getUniversityId())) {
            List<Long> uniIds = new ArrayList<>();
            uniIds.add(university.getUniversityId());
            University university1 = universityRepository.findById(university.getUniversityId()).get();
            university1.setUniversityName(university.getUniversityName());
            university1.setUniversityAddress(university.getUniversityAddress());
            if (!universityRepository.existsByUniversityEmailAndUniversityIdNotIn(university.getUniversityEmail(), uniIds)) {
                university1.setUniversityEmail(university.getUniversityEmail());
            }
            if (!universityRepository.existsByUniversityContactNumberAndUniversityIdNotIn(university.getUniversityContactNumber(), uniIds)) {
                university1.setUniversityContactNumber(university.getUniversityContactNumber());
            }
            university1.setUniversityEstablishedDate(university.getUniversityEstablishedDate());
            universityRepository.save(university1);
            return "Updated Successfully!!!";

        } else {
            if (universityRepository.existsByUniversityEmail(university.getUniversityEmail())) {
                throw new IdNotFound("Email Already Exist!!!");
            } else if (universityRepository.existsByUniversityContactNumber(university.getUniversityContactNumber())) {
                throw new IdNotFound("Contact Number already Exist!!!");
            } else {
                universityRepository.save(university);
                return "Successfully inserted!!!";
            }
        }
    }

    @Override
    public Object getUniversityById(Long universityId) {
        Optional<University> university = universityRepository.findById(universityId);
        if (university.isPresent()) {
            return university.get();
        } else {
            throw new IdNotFound("Id Not Found!!!");
        }
    }

    @Override
    public Object getAllUniversity() {
        return universityRepository.findAll();
    }

    @Override
    public Object softDeleteUniversityById(Long universityId) {
        if (universityRepository.existsById(universityId)) {
            University university = universityRepository.findById(universityId).get();
            university.setIsDeleted(true);
            universityRepository.save(university);
            return "Deleted Successfully!!!";
        } else {
            throw new IdNotFound("Id Not Found!!!");
        }
    }

    @Override
    public Object changeStatus(Long universityID) {
        if (universityRepository.existsById(universityID)) {
            University university = universityRepository.findById(universityID).get();
            if (university.isActive()) {
                university.setActive(false);
            } else {
                university.setActive(true);
            }
            universityRepository.save(university);
            return "Change Active Status Successfully!!!";
        } else {
            throw new IdNotFound("Id Not Exist!!!");
        }
    }

    @Override
    public Object getCollegeDetailsAndStudentDetailsUnderUniversity(Long universityId) {
        if (universityRepository.existsByUniversityIdAndIsDeleted(universityId, false)) {

            University university = universityRepository.findById(universityId).get();
            List<College> collegeList = collegeRepository.findByUniversityId(university.getUniversityId());
            university.setCollegeList(collegeList);
            long count = 0 ;
//            collegeList.stream()
//                        .filter(college -> studentRepository.existsByCollegeId(college.getCollegeId()))
//                    .forEach(college -> college.setCountStudents(count));
//            for (College college: collegeList){
//                if(studentRepository.existsByCollegeId(college.getCollegeId())) {
////                   count++;
//                    List<Student> studentList = studentRepository.findByCollegeId(college.getCollegeId());
//                    System.out.println(studentList);
//                    count++;
//                    college.setCountStudents(count);
//                }
//            }
            for (int i = 0; i < collegeList.size(); i++){
//                System.out.println(collegeList.get(i));
                if(studentRepository.existsByCollegeId(collegeList.get(i).getCollegeId())){
                    List<Student> student = studentRepository.findByCollegeId(collegeList.get(i).getCollegeId());
//                    System.out.println(student);
                    collegeList.get(i).setCountStudents((long) student.size());
                }
            }
            return university;
        } else {
            throw new IdNotFound("University Id Not Exist!!");
        }
    }
}
