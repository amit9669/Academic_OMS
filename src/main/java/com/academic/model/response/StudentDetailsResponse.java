package com.academic.model.response;

import com.academic.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDetailsResponse {

    private Long studentId;
    private String studentName;
    private Gender gender;
    private String studentEmail;
    private String studentMobileNo;
    private Long courseId;
    private Long streamId;
    private Long collegeId;
    private String collegeName;
    private String universityName ;
    private String courseName;
    private String streamName;
    private Double courseFees;
    private String courseYear;
}
