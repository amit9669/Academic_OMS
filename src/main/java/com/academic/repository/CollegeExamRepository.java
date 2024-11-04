package com.academic.repository;

import com.academic.model.CollegeExams;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollegeExamRepository extends JpaRepository<CollegeExams,Long> {
    boolean existsByCollegeExamIdAndIsDeleted(Long collegeExamId, boolean b);
}
