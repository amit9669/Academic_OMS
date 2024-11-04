package com.academic.repository;

import com.academic.model.StudentExamDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentExamRepository extends JpaRepository<StudentExamDetails ,Long> {
    boolean existsByStudentId(Long studentId);

    StudentExamDetails findByStudentId(Long studentId);
}
