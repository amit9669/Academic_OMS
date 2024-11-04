package com.academic.repository;

import com.academic.model.Student;
import com.academic.model.response.StudentDetailsResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByStudentEmail(String studentEmail);

    boolean existsByStudentMobileNo(String studentMobileNo);

    boolean existsByStudentEmailAndStudentIdNotIn(String studentEmail, List<Long> stIds);

    boolean existsByStudentMobileNoAndStudentIdNotIn(String studentMobileNo, List<Long> stIds);

    boolean existsByStudentIdAndIsDeleted(Long classId, boolean isDeleted);

    boolean existsByCollegeId(Long collegeId);

    List<Student> findByCollegeId(Long collegeId);

    @Query(value = "SELECT DISTINCT new com.academic.model.response.StudentDetailsResponse(s.studentId, \n" +
            "CONCAT(s.studentFirstName,' ',s.studentMiddleName,' ',s.studentLastName), \n" +
            "s.gender,s.studentEmail,s.studentMobileNo,s.studentCourseId,s.studentStreamId,s.collegeId, \n" +
            "c.collegeName,un.universityName,cm.courseName,sm.streamName,cf.feesAmount,cf.year) \n" +
            "FROM Student AS s LEFT JOIN College AS c \n" +
            "ON s.collegeId=c.collegeId LEFT JOIN University AS un ON c.universityId=un.universityId LEFT JOIN \n" +
            "CourseMaster AS cm ON s.studentCourseId=cm.courseId LEFT JOIN StreamMaster AS sm ON s.studentStreamId=sm.streamId LEFT JOIN \n" +
            "CourseFeesDetails AS cf ON s.studentCourseId=cf.courseId INNER JOIN CoursePursuingDetails AS cpd \n" +
            "ON s.studentCourseId=cpd.courseId WHERE \n" +
            "s.isDeleted=false AND c.isDeleted=false AND un.isDeleted=false AND cm.isDeleted=false AND sm.isDeleted=false AND \n" +
            "(COALESCE(:studentMobileNo,'')='' OR TRIM(LOWER(s.studentMobileNo)) IN (:studentMobileNo) ) And\n" +
            "(TRIM(LOWER(CONCAT(s.studentFirstName,' ',s.studentLastName))) LIKE CONCAT('%',COALESCE(:studentFullName,' '),'%') OR \n" +
            "TRIM(LOWER(CONCAT(s.studentFirstName,' ',s.studentMiddleName,' ',s.studentLastName))) LIKE CONCAT('%',COALESCE(:studentFullName,' '),'%'))AND \n" +
            "(COALESCE(:collegeName,'')='' OR TRIM(LOWER(c.collegeName)) IN (:collegeName)) And \n" +
            "(COALESCE(:courseName,'')='' OR TRIM(LOWER(cm.courseName)) IN (:courseName)) And \n" +
            "(COALESCE(:streamName,'')='' OR TRIM(LOWER(sm.streamName)) IN (:streamName)) AND \n" +
            "(COALESCE(:universityName,'')='' OR TRIM(LOWER(un.universityName)) IN (:universityName))AND \n" +
            "cf.feesAmount BETWEEN :startFeesAmount AND :endFeesAmount AND \n " +
            "cpd.courseYear=cf.year ORDER BY s.studentId ASC", nativeQuery = false)
    List<StudentDetailsResponse> getAllDetails(@Param("collegeName") List<String> collegeName,
                                               @Param("studentMobileNo") List<String> studentMobileNo,
                                               @Param("courseName") List<String> courseName,
                                               @Param("streamName") List<String> streamName,
                                               @Param("universityName") List<String> universityName,
                                               @Param("studentFullName") String studentFullName,
                                               @Param("startFeesAmount") Double startFeesAmount,
                                               @Param("endFeesAmount") Double endFeesAmount);

    @Query(value = "SELECT s.createdAt FROM Student AS s WHERE s.studentId = :studentId",nativeQuery = false)
    LocalDateTime getCreatedAtByStudentId(Long studentId);

    @Modifying
    @Transactional // Ensure this method runs in a transactional context
    @Query(value = "UPDATE Student AS s SET s.updatedAt = CURRENT_TIMESTAMP WHERE s.studentId = :studentId", nativeQuery = false)
    void updateTimestampByStudentId(Long studentId);
}