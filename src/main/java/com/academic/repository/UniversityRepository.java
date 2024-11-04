package com.academic.repository;

import com.academic.model.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {

    boolean existsByUniversityEmail(String universityEmail);

    boolean existsByUniversityContactNumber(Long universityContactNumber);

    boolean existsByUniversityEmailAndUniversityIdNotIn(String universityEmail, List<Long> uniIds);

    boolean existsByUniversityContactNumberAndUniversityIdNotIn(Long universityContactNumber, List<Long> uniIds);

    boolean existsByUniversityIdAndIsDeleted(Long universityId, boolean b);
}
