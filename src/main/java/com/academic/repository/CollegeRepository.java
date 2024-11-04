package com.academic.repository;

import com.academic.model.College;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CollegeRepository extends JpaRepository<College ,Long> {

    boolean existsByCollegeContactNumber(Long collegeContactNumber);

    boolean existsByCollegeEmail(String collegeEmail);

    boolean existsByCollegeEmailAndCollegeIdNotIn(String collegeEmail, List<Long> clIds);

    boolean existsByCollegeContactNumberAndCollegeIdNotIn(Long collegeContactNumber, List<Long> clIds);

    boolean existsByCollegeIdAndIsDeleted(Long collegeId, boolean b);

    boolean existsByCollegeEmailIgnoreCase(String email);

    boolean existsByCollegePassword(String password);

    boolean existsByCollegeEmailIgnoreCaseAndIsActiveAndIsDeleted(String email, boolean b, boolean b1);

    College findByCollegeEmailIgnoreCase(String email);

    Optional<College> findByCollegeEmail(String email);

    List<College> findByUniversityId(Long universityId);

    boolean existsByCollegeNameIgnoreCase(String collegeName);

    College findByCollegeNameIgnoreCase(String collegeName);

}
