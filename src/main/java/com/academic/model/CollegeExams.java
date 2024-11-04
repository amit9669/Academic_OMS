package com.academic.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "college_exams")
public class CollegeExams {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "college_exam_id")
    private Long collegeExamId ;

    @Column(name = "college_id")
    private Long collegeId ;

    @Column(name = "class_id")
    private Long classId ;

    @Column(name = "subject_id")
    private Long subjectId ;

    @Enumerated(EnumType.STRING)
    @Column(name = "exam_pattern")
    private ExamPattern  examPattern ;

    @Column(name = "exam_start_date")
    private LocalDate examStartDate ;

    @Column(name = "exam_start_time")
    private LocalTime examStartTime ;

    @Column(name = "exam_end_time")
    private LocalTime examEndTime ;

    @Column(name = "passing_marks")
    private Long passingMarks ;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false ;

    @Column(name = "is_active")
    private boolean isActive = true ;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt ;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt ;
}
