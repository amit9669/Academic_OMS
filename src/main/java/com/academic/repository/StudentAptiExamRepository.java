package com.academic.repository;

import com.academic.model.StudentExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentAptiExamRepository extends JpaRepository<StudentExam,Long> {

    @Query(value = "SELECT se FROM StudentExam AS se WHERE se.studentId = :studentId",nativeQuery = false)
    List<StudentExam> getStudentById(Long studentId);
}
