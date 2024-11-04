package com.academic.repository;

import com.academic.model.CourseMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseMasterRepository extends JpaRepository<CourseMaster, Long> {

    boolean existsByCourseName(String courseName);

    boolean existsByCourseNameAndCourseIdNotIn(String courseName, List<Long> couIds);

    boolean existsByCourseIdAndIsDeleted(Long studentCourseId, boolean b);

    @Query(value = "select cm.courseName from CourseMaster as cm where cm.courseId=:studentCourseId and cm.isDeleted=false",nativeQuery = false)
   // @Query(value = "select cm.course_name from course_master as cm where cm.course_id=:studentCourseId and cm.is_deleted='false'",nativeQuery = true)
    String getCourseNameById(Long studentCourseId);

    @Query(value = "SELECT cm.period from CourseMaster AS cm WHERE cm.courseId= :courseId",nativeQuery = false)
    Integer getPeriodByCourseId(Long courseId);
}
