package com.academic.repository;

import com.academic.model.ClassDetails;
import com.academic.model.Division;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassDetailsRepository extends JpaRepository<ClassDetails,Long> {

    boolean existsByClassIdAndIsDeleted(Long classId, boolean b);

    boolean existsByDivision(Division division);
    
    boolean existsByStreamId(Long streamID);

    boolean existsByCourseId(Long courseId);

    boolean existsByCourseIdAndStreamIdAndDivision(Long courseId, Long streamID, Division division);

    boolean existsByCourseIdAndStreamIdAndDivisionAndClassIdNotIn(Long courseId, Long streamId, Division division, List<Long> classIds);
}
