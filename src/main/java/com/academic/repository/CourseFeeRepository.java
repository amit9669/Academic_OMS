package com.academic.repository;

import com.academic.model.CourseFeesDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseFeeRepository extends JpaRepository<CourseFeesDetails,Long> {
}
