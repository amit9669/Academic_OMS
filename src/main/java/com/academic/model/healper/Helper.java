package com.academic.model.healper;

import com.academic.exception.IdNotFound;
import com.academic.model.Gender;
import com.academic.model.Student;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Helper {

    public static String[] HEADERS = {
            "studentId",
            "collegeId",
            "studentFirstName",
            "studentLastName",
            "studentMiddleName",
            "gender",
            "studentEmail",
            "studentMobileNo",
            "studentStreamId",
            "studentCourseId",
            "classId",
            "createdAt",
            "updatedAt"
    };

    public static String SHEET_NAME = "student_data";

    public static boolean checkExcelFormat(MultipartFile file) {

        String contentType = file.getContentType();
        if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            return true;
        } else {
            return false;
        }
    }

    public static List<Student> convertExcelToListProduct(InputStream stream) throws IOException {

        List<Student> studentList = new ArrayList<>();

        try {

            XSSFWorkbook workbook = new XSSFWorkbook(stream);

            XSSFSheet sheet = workbook.getSheet("studentDetails");

            int rowNumber = 0;
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellIterator = row.iterator();

                int cellId = 0;

                Student student = new Student();
                while (cellIterator.hasNext()) {

                    Cell cell = cellIterator.next();

                    switch (cellId) {
                        case 0:
                            student.setStudentId((long) cell.getNumericCellValue());
                            break;
                        case 1:
                            student.setCollegeId((long) cell.getNumericCellValue());
                            break;
                        case 2:
                            student.setStudentFirstName(cell.getStringCellValue());
                            break;
                        case 3:
                            student.setStudentLastName(cell.getStringCellValue());
                            break;
                        case 4:
                            student.setStudentMiddleName(cell.getStringCellValue());
                            break;
                        case 5:
                            student.setGender(Gender.valueOf(cell.getStringCellValue()));
                            break;
                        case 6:
                            student.setStudentEmail(cell.getStringCellValue());
                            break;
                        case 7:
                            student.setStudentMobileNo(String.valueOf((long) cell.getNumericCellValue()));
                            break;
                        case 8:
                            student.setStudentStreamId((long) cell.getNumericCellValue());
                            break;
                        case 9:
                            student.setStudentCourseId((long) cell.getNumericCellValue());
                            break;
                        case 10:
                            student.setClassId((long) cell.getNumericCellValue());
                            break;
                        default:
                            break;
                    }
                    cellId++;
                }
                studentList.add(student);
            }
            return studentList;
        } catch (IdNotFound e) {
            throw new IdNotFound("File Error!");
        }

    }
}
