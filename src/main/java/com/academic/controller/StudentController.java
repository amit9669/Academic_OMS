package com.academic.controller;

import com.academic.exception.IdNotFound;
import com.academic.model.Student;
import com.academic.model.healper.Helper;
import com.academic.model.request.CollegeRequest;
import com.academic.model.response.EntityResponse;
import com.academic.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@CrossOrigin("*")
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private IStudentService iStudentService;

    @PostMapping("/saveOrUpdateStudent")
    public ResponseEntity<?> saveOrUpdateStudent(@RequestBody Student student) {
        try {
            return new ResponseEntity<>(iStudentService.saveOrUpdateStudent(student), HttpStatus.OK);
        } catch (IdNotFound e) {
            return new ResponseEntity<>(new EntityResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getStudentByID/{studentId}")
    public ResponseEntity<?> getStudentByID(@PathVariable Long studentId) {
        try {
            return new ResponseEntity<>(iStudentService.getStudentById(studentId), HttpStatus.FOUND);
        } catch (IdNotFound e) {
            return new ResponseEntity<>(new EntityResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllStudent")  /*It's export the data in Excel*/
    public void getAllStudent(HttpServletResponse response) throws IOException {

      response.setContentType("application/octet-stream");

      response.setHeader("Content-Disposition","attachment;filename=student_data.xls");

      iStudentService.getAllStudent(response);

    }

    @DeleteMapping("/softDeleteStudentById/{studentId}")
    public ResponseEntity<?> softDeleteStudent(@PathVariable Long studentId) {
        try {
            return new ResponseEntity<>(iStudentService.softDeleteStudentById(studentId), HttpStatus.OK);
        } catch (IdNotFound e) {
            return new ResponseEntity<>(new EntityResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/changeStatus/{studentId}")
    public ResponseEntity<?> changeStatus(@PathVariable Long studentId) {
        try {
            return new ResponseEntity<>(iStudentService.changeStatus(studentId), HttpStatus.OK);
        } catch (IdNotFound e) {
            return new ResponseEntity<>(new EntityResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllDetailsOfStudent/{studentId}")
    public ResponseEntity<?> getAllDetailsOfStudent(@PathVariable Long studentId) {
        try {
            return new ResponseEntity<>(iStudentService.getAllDetailsOfStudentById(studentId), HttpStatus.OK);
        } catch (IdNotFound e) {
            return new ResponseEntity<>(new EntityResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/ByCollegeName")
    public ResponseEntity<?> getAllStudentByUsingList(@RequestBody CollegeRequest collegeRequest) throws IOException {
        try{
            return new ResponseEntity<>(iStudentService.getStudentByCollegeName(collegeRequest.getCollegeName(),
                    collegeRequest.getStudentName(),collegeRequest.getStudentMobileNo(),
                    collegeRequest.getCourseName(),collegeRequest.getStreamName(),collegeRequest.getUniversityName(),
                    collegeRequest.getStartFeesAmount(),collegeRequest.getEndFeesAmount()),HttpStatus.OK);

        }catch(IdNotFound e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/importExcel") /*It's import the data excel to DataBase*/
    public ResponseEntity<?> importExcelFile(@RequestParam("file") MultipartFile file){
        try{
            if(Helper.checkExcelFormat(file)){
                return new ResponseEntity<>(iStudentService.saveFile(file),HttpStatus.OK);
            }else{
                throw new IdNotFound("file is Empty!!");
            }
        }catch(IdNotFound e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }
    }
}
