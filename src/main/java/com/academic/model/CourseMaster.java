package com.academic.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "course_master")
public class CourseMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId ;

    @Column(name = "course_name")
    private String courseName ;

    @Column(name = "course_period")
    private Integer period ;

    @Column(name = "course_language")
    private String courseLanguage ;

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
