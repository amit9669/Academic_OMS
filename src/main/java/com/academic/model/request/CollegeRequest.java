package com.academic.model.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class CollegeRequest {

    private String studentName;
    private List<String> studentMobileNo;
    private List<String> collegeName;
    private List<String> universityName ;
    private List<String> courseName;
    private List<String> streamName;
    private Double startFeesAmount;
    private Double endFeesAmount;
}
