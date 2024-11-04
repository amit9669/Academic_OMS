package com.academic.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "class_details")
public class ClassDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private Long classId ;

    @Column(name = "stream_id")
    private Long streamId ;

    @Column(name = "course_id")
    private Long courseId ;

    @Column(name = "division")
    @Enumerated(EnumType.STRING)
    private Division division ;

    @Column(name = "total_student_count")
    private Long totalStudentsCount ;

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
