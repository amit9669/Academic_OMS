package com.academic.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "course_fees_details")
public class CourseFeesDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fees_id")
    private Long feesId;

    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "year")
    private String year;

    @Column(name = "fees_amount")
    private Double feesAmount;
}
