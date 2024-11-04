package com.academic.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_Id")
    private Long studentId;

    @Column(name = "college_id")
    private Long collegeId;

    @Column(name = "student_first_name")
    private String studentFirstName;

    @Column(name = "student_last_name")
    private String studentLastName;

    @Column(name = "student_middle_name")
    private String studentMiddleName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "student_email")
    private String studentEmail;

    @Column(name = "student_mobile_no")
    private String studentMobileNo;

    @Column(name = "student_stream_id")
    private Long studentStreamId;

    @Column(name = "student_course_id")
    private Long studentCourseId;

    @Column(name = "class_id")
    private Long classId;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @Column(name = "is_active")
    private boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

//    @Transient
//    private String courName;

//    @Transient
//    private CourseMaster courseMaster;

    @Transient
    private String studentUniversityName;

    @Transient
    private String studentCollegeName;

    @Transient
    private String studentCourseName;

    @Transient
    private String studentStreamName;

    @Transient
    private ExamPattern examPattern;

    @Transient
    private String studentSubjectName;

    @Transient
    private LocalDate studentExamStartedDate;

    @Transient
    private Division studentDivision;

    @Transient
    private int studentMarksObtained;

    @Transient
    private Long studentOutOfMarks ;

}
