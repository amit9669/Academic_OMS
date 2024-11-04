package com.academic.service.Impl;

import com.academic.exception.IdNotFound;
import com.academic.model.*;
import com.academic.model.healper.Helper;
import com.academic.model.response.StudentDetailsResponse;
import com.academic.repository.*;
import com.academic.service.IStudentService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService implements IStudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CollegeRepository collegeRepository;

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private CourseMasterRepository courseMasterRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private StreamMasterRepository streamMasterRepository;

    @Autowired
    private ClassDetailsRepository classDetailsRepository;

    @Autowired
    private CollegeExamRepository collegeExamRepository;

    @Autowired
    private StudentExamRepository studentExamRepository;

    @Override
    public Object saveOrUpdateStudent(Student student) {

        if (studentRepository.existsById(student.getStudentId())) {
            List<Long> stIds = new ArrayList<>();
            stIds.add(student.getStudentId());
            Student student1 = studentRepository.findById(student.getStudentId()).get();

            if (!studentRepository.existsByStudentEmailAndStudentIdNotIn(student.getStudentEmail(), stIds)) {
                student1.setStudentEmail(student.getStudentEmail());
            }

            if (!studentRepository.existsByStudentMobileNoAndStudentIdNotIn(student.getStudentMobileNo(), stIds)) {
                student1.setStudentMobileNo(student.getStudentMobileNo());
            }
            if (student.getCollegeId() != null) {
                if (!collegeRepository.existsByCollegeIdAndIsDeleted(student.getCollegeId(), false)) {
                    throw new IdNotFound("College Id Not Exist!!");
                } else {
                    student1.setCollegeId(student.getCollegeId());
                }
            }
            if (student.getStudentCourseId() != null) {
                if (!courseMasterRepository.existsByCourseIdAndIsDeleted(student.getStudentCourseId(), false)) {
                    throw new IdNotFound("Course Id Not Exist!!");
                } else {
                    student1.setStudentCourseId(student.getStudentCourseId());
                }
            }
            if (student.getStudentStreamId() != null) {
                if (!streamMasterRepository.existsByStreamIdAndIsDeleted(student.getStudentStreamId(), false)) {
                    throw new IdNotFound("Stream Id Not Exist!!");
                } else {
                    student1.setStudentStreamId(student.getStudentStreamId());
                }
            }
            if (student.getClassId() != null) {
                if (!classDetailsRepository.existsByClassIdAndIsDeleted(student.getClassId(), false)) {
                    throw new IdNotFound("Class Id Not Exist!!");
                } else {
                    student1.setClassId(student.getClassId());
                }
            }
            student1.setGender(student.getGender());
            student1.setStudentFirstName(student.getStudentFirstName());
            student1.setStudentLastName(student.getStudentLastName());
            student1.setStudentMiddleName(student.getStudentMiddleName());
            studentRepository.save(student1);
            return "Updated Successfully!!!";
        } else {
            if (!collegeRepository.existsByCollegeIdAndIsDeleted(student.getCollegeId(), false)) {
                throw new IdNotFound("College Id Not Exist!!");
            } else if (!streamMasterRepository.existsByStreamIdAndIsDeleted(student.getStudentStreamId(), false)) {
                throw new IdNotFound("Stream Id Not Exist!!");
            } else if (!courseMasterRepository.existsByCourseIdAndIsDeleted(student.getStudentCourseId(), false)) {
                throw new IdNotFound("Course Id Not Exist!!");
            } else if (!classDetailsRepository.existsByClassIdAndIsDeleted(student.getClassId(), false)) {
                throw new IdNotFound("Class Id Not Exist!!");
            } else {
                if (studentRepository.existsByStudentEmail(student.getStudentEmail())) {
                    throw new IdNotFound("Email Already Exist!!");
                } else if (studentRepository.existsByStudentMobileNo(student.getStudentMobileNo())) {
                    throw new IdNotFound("Mobile Number Already Exist!!");
                } else {
                    studentRepository.save(student);
                    return "Inserted Successfully!!!";
                }
            }
        }
    }

    @Override
    public Object getStudentById(Long studentId) {
        if (studentRepository.existsById(studentId)) {
            return studentRepository.findById(studentId);
        } else {
            throw new IdNotFound("Student Id Not Exist!!");
        }
    }

    @Override
    public void getAllStudent(HttpServletResponse response) throws IOException {

        List<Student> studentList = studentRepository.findAll();

        if(studentList.isEmpty()){
            throw new IdNotFound("Data Not Available");
        }else{
            try(Workbook workbook = new XSSFWorkbook()) {
//            Create sheet
                Sheet sheet = workbook.createSheet(Helper.SHEET_NAME);

//            create Row : Header Row
                Row row = sheet.createRow(0);

//            create cell
                for (int i = 0; i < Helper.HEADERS.length; i++) {
                    Cell cell = row.createCell(i);
                    cell.setCellValue(Helper.HEADERS[i]);
                }
//            Value rows
                int rowIndex = 1;

                for (Student studentResponse : studentList) {
                    Row dataRow = sheet.createRow(rowIndex++);
                    dataRow.createCell(0).setCellValue(studentResponse.getStudentId());
                    dataRow.createCell(1).setCellValue(studentResponse.getCollegeId());
                    dataRow.createCell(2).setCellValue(studentResponse.getStudentFirstName());
                    dataRow.createCell(3).setCellValue(studentResponse.getStudentLastName());
                    dataRow.createCell(4).setCellValue(studentResponse.getStudentMiddleName());
                    dataRow.createCell(5).setCellValue(studentResponse.getGender().name());
                    dataRow.createCell(6).setCellValue(studentResponse.getStudentEmail());
                    dataRow.createCell(7).setCellValue(studentResponse.getStudentMobileNo());
                    dataRow.createCell(8).setCellValue(studentResponse.getStudentStreamId());
                    dataRow.createCell(9).setCellValue(studentResponse.getStudentCourseId());
                    dataRow.createCell(10).setCellValue(studentResponse.getClassId());
                    dataRow.createCell(11).setCellValue(String.valueOf(studentResponse.getCreatedAt()));
                    dataRow.createCell(12).setCellValue(String.valueOf(studentResponse.getUpdatedAt()));
                }
                ServletOutputStream stream = response.getOutputStream();
                workbook.write(stream);
                workbook.close();
                stream.close();

            }catch(IOException e){
                throw new RuntimeException("Failed to export data to Excel file: " + e.getMessage());
            }
        }

    }

    @Override
    public Object softDeleteStudentById(Long studentId) {
        if (studentRepository.existsById(studentId)) {
            Student student = studentRepository.findById(studentId).get();
            student.setIsDeleted(true);
            studentRepository.save(student);
            return "Deleted Successfully!!!";
        } else {
            throw new IdNotFound("Student Id Not Exist!!");
        }
    }

    @Override
    public Object changeStatus(Long studentId) {
        if (studentRepository.existsById(studentId)) {
            Student student = studentRepository.findById(studentId).get();
            if (student.isActive()) {
                student.setActive(false);
            } else {
                student.setActive(true);
            }
            studentRepository.save(student);
            return "Change Student Active Status!!!";
        } else {
            throw new IdNotFound("Student not Exist!!!");
        }
    }

    @Override
    public Object getAllDetailsOfStudentById(Long studentId) {
        if (studentRepository.existsById(studentId)) {
            Student student = studentRepository.findById(studentId).get();

            if (student.getCollegeId() != null) {
                College college = collegeRepository.findById(student.getCollegeId()).get();
                student.setStudentCollegeName(college.getCollegeName());

                if (college.getUniversityId() != null) {
                    University university = universityRepository.findById(college.getUniversityId()).get();
                    student.setStudentUniversityName(university.getUniversityName());
                }

                if (student.getStudentCourseId() != null) {
                    CourseMaster courseMaster = courseMasterRepository.findById(student.getStudentCourseId()).get();
                    student.setStudentCourseName(courseMaster.getCourseName());
                }

                if (student.getStudentStreamId() != null) {
                    StreamMaster streamMaster = streamMasterRepository.findById(student.getStudentStreamId()).get();
                    student.setStudentStreamName(streamMaster.getStreamName());
                }

                if (student.getClassId() != null) {
                    ClassDetails classDetails = classDetailsRepository.findById(student.getClassId()).get();
                    student.setStudentDivision(classDetails.getDivision());
                }
            }

            if (studentExamRepository.existsByStudentId(studentId)) {
                StudentExamDetails studentExamDetails = studentExamRepository.findByStudentId(studentId);
                student.setStudentMarksObtained(studentExamDetails.getMarksObtained());
                student.setStudentOutOfMarks(studentExamDetails.getOutOfMarks());

                if (collegeExamRepository.existsById(studentExamDetails.getCollegeExamId())) {
                    CollegeExams collegeExams = collegeExamRepository.findById(studentExamDetails.getCollegeExamId()).get();
                    student.setExamPattern(collegeExams.getExamPattern());
                    student.setStudentExamStartedDate(collegeExams.getExamStartDate());
                    if (subjectRepository.existsById(collegeExams.getSubjectId())) {
                        SubjectMaster subjectMaster = subjectRepository.findById(collegeExams.getSubjectId()).get();
                        student.setStudentSubjectName(subjectMaster.getSubjectName());
                    }
                }
            } else {
                student.setStudentMarksObtained(0);
                student.setStudentOutOfMarks(0L);
            }
            return student;
        } else {
            throw new IdNotFound("Student Id Not Exist!!");
        }
    }

    @Override
    public Object getStudentByCollegeName(List<String> collegeName, String studentFullName, List<String> studentMobileNo,
                                          List<String> courseName,
                                          List<String> streamName, List<String> universityName,
                                          Double startFeesAmount, Double endFeesAmount) throws IOException {

        List<StudentDetailsResponse> responseList = new ArrayList<>();
        if (collegeName != null && !collegeName.isEmpty()) {
            collegeName = collegeName.stream()
                    .map(String::toLowerCase)
                    .map(String::trim)
                    .collect(Collectors.toList());
        }
        if (studentMobileNo != null && !studentMobileNo.isEmpty()) {
            studentMobileNo = studentMobileNo.stream()
                    .map(String::toLowerCase)
                    .map(String::trim)
                    .collect(Collectors.toList());
        }
        if (courseName != null && !courseName.isEmpty()) {
            courseName = courseName.stream()
                    .map(String::toLowerCase)
                    .map(String::trim)
                    .collect(Collectors.toList());
        }
        if (streamName != null && !streamName.isEmpty()) {
            streamName = streamName.stream()
                    .map(String::toLowerCase)
                    .map(String::trim)
                    .collect(Collectors.toList());
        }
        if (universityName != null && !universityName.isEmpty()) {
            universityName = universityName.stream()
                    .map(String::toLowerCase)
                    .map(String::trim)
                    .collect(Collectors.toList());
        }
        if (studentFullName != null && !studentFullName.isEmpty()) {
            studentFullName.toLowerCase().trim();
        }
        responseList = studentRepository.getAllDetails(collegeName, studentMobileNo, courseName,
                streamName, universityName, studentFullName, startFeesAmount, endFeesAmount);

       return responseList;
    }

    @Override
    public Object saveFile(MultipartFile file) {

        try {
            List<Student> studentList = Helper.convertExcelToListProduct(file.getInputStream());
            System.out.println(studentList);
            studentRepository.saveAll(studentList);
            return "Excel Data Inserted!!";
        } catch (IOException e) {
            e.printStackTrace();
            throw new IdNotFound("Excel Error!!!");
        }
    }
}
