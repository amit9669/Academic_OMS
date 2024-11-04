package com.academic.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "student_exam_details")
public class StudentExamDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_exam_id")
    private Long studentExamId ;

    @Column(name = "student_id")
    private Long studentId ;

    @Column(name = "college_exam_id")
    private Long collegeExamId ;

    @Column(name = "marks_obtained")
    private int marksObtained ;

    @Column(name = "out_of_marks")
    private Long outOfMarks ;

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
