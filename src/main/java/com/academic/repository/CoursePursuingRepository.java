package com.academic.repository;

import com.academic.model.CoursePursuingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CoursePursuingRepository extends JpaRepository<CoursePursuingDetails,Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE CoursePursuingDetails AS cpd SET cpd.courseYear = :secondYear")
    void updateCourseYear(String secondYear);
}
