package com.academic.service;

import com.academic.model.Student;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface IStudentService {

    public Object saveOrUpdateStudent(Student student);

    public Object getStudentById(Long studentId);

    public void getAllStudent(HttpServletResponse response) throws IOException;

    public Object softDeleteStudentById(Long studentId);

    public Object changeStatus(Long studentId);

    public Object getAllDetailsOfStudentById(Long studentId);

    public Object getStudentByCollegeName(List<String> collegeName,String studentFullName,
                                          List<String> studentMobileNo, List<String> courseName,
                                          List<String> streamName, List<String> universityName,
                                          Double startFeesAmount,Double endFeesAmount) throws IOException;

    public Object saveFile(MultipartFile file);
}
