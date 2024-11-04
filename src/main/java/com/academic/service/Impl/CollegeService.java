package com.academic.service.Impl;

import com.academic.exception.IdNotFound;
import com.academic.jwt.AuthenticationService;
import com.academic.jwt.JwtService;
import com.academic.model.College;
import com.academic.model.request.LogInRequest;
import com.academic.model.request.OTPRequest;
import com.academic.model.response.LogInResponse;
import com.academic.repository.CollegeRepository;
import com.academic.repository.UniversityRepository;
import com.academic.service.ICollegeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CollegeService implements ICollegeService {

    private Integer otp;

    private LocalTime otpGenerationTime;

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final AuthenticationService authenticationService;

    public CollegeService(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private CollegeRepository collegeRepository;

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public Object saveOrUpdateCollege(College college) {

        if (collegeRepository.existsById(college.getCollegeId())) {
            List<Long> clIds = new ArrayList<>();
            clIds.add(college.getCollegeId());

            College college1 = collegeRepository.findById(college.getCollegeId()).get();
            college1.setCollegeName(college.getCollegeName());
            college1.setCollegePassword(college.getCollegePassword());
            college1.setCollegeAddress(college.getCollegeAddress());
            if (!collegeRepository.existsByCollegeEmailAndCollegeIdNotIn(college.getCollegeEmail(), clIds)) {
                college1.setCollegeEmail(college.getCollegeEmail());
            }
            if (!collegeRepository.existsByCollegeContactNumberAndCollegeIdNotIn(college.getCollegeContactNumber(), clIds)) {
                college1.setCollegeContactNumber(college.getCollegeContactNumber());
            }
            if (universityRepository.existsByUniversityIdAndIsDeleted(college.getUniversityId(), false)) {
                college1.setUniversityId(college.getUniversityId());
                collegeRepository.save(college1);
                this.sendEmail(college.getCollegeEmail(),
                        "Successfully Updated Your Profile!!",
                        "Welcome " + college.getCollegeName() + " to our Dashboard!!");
                return "Updated Successfully!!!";
            } else {
                throw new IdNotFound("University not Exist!!!");
            }
        } else {
            if (collegeRepository.existsByCollegeContactNumber(college.getCollegeContactNumber())) {
                throw new IdNotFound("Contact Number Already Exist!!");
            } else if (collegeRepository.existsByCollegeEmail(college.getCollegeEmail())) {
                throw new IdNotFound("College Email Already Exist!!");
            } else if (!universityRepository.existsByUniversityIdAndIsDeleted(college.getUniversityId(), false)) {
                throw new IdNotFound("University not exist!!!");
            } else {
                college.setCollegePassword(college.getCollegePassword());

                collegeRepository.save(college);
                this.sendEmail(college.getCollegeEmail(),
                        "Successfully Registered!!",
                        "Welcome " + college.getCollegeName() + " to our Dashboard!!");
                return "Inserted Successfully!!!";
            }
        }
    }

    @Override
    public Object getCollegeById(Long collegeId) throws Exception {
        Optional<College> optional = collegeRepository.findById(collegeId);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new IdNotFound("Id not Exist!!!");
        }
    }

    @Override
    public Object getAllCollege() {
        return collegeRepository.findAll();
    }

    @Override
    public Object softDeleteCollegeById(Long collegeId) {
        if (collegeRepository.existsById(collegeId)) {
            College college = collegeRepository.findById(collegeId).get();
            college.setIsDeleted(true);
            collegeRepository.save(college);
            return "Deleted Successfully!!!";
        } else {
            throw new IdNotFound("College Id Not Found!!!");
        }
    }

    @Override
    public Object changeStatus(Long collegeId) {
        if (collegeRepository.existsById(collegeId)) {
            College college = collegeRepository.findById(collegeId).get();
            if (college.isActive()) {
                college.setActive(false);
            } else {
                college.setActive(true);
            }
            collegeRepository.save(college);
            return "Change College Active Status";
        } else {
            throw new IdNotFound("Id Not Exist!!!");
        }
    }

    @Override
    public Object logIn(LogInRequest logInRequest) {
        if (collegeRepository.existsByCollegeEmailIgnoreCaseAndIsActiveAndIsDeleted(logInRequest.getEmail(), true, false)) {
            College college = collegeRepository.findByCollegeEmailIgnoreCase(logInRequest.getEmail());
            if (passwordEncoder.matches(logInRequest.getPassword(), college.getCollegePassword())) {
                OTPRequest otpRequest = new OTPRequest();
                otpRequest.setOtp(generateOTP());
                otpGenerationTime = LocalTime.now();
                this.sendEmail(college.getCollegeEmail(),
                        "Successfully LogIn!!",
                        "Hi " + college.getCollegeName() + ",\n Your OTP is : " + otpRequest.getOtp());

                College authenticate = authenticationService.authenticate(logInRequest);
                String jwtToken = jwtService.generateToken(authenticate);

                return LogInResponse.builder().jwtToken(jwtToken).build();
//                return "jwtToken : "+jwtToken;
            } else {
                throw new IdNotFound("Password wrong Please Try again!!");
            }
        } else {
            throw new IdNotFound("College Email Not Exist!!");
        }
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(to);
            mail.setSubject(subject);
            mail.setText(body);
            javaMailSender.send(mail);

        } catch (IdNotFound e) {
            log.error("Exception while send Email ", e);
        }
    }

    @Override
    public Object checkOTP(OTPRequest otpRequest) {
        LocalTime verifyTime = LocalTime.now();
        Duration duration = Duration.between(otpGenerationTime, verifyTime);
        if (duration.toMinutes() < 1) {
            if (otp.equals(otpRequest.getOtp()) && otp != 0) {
                otp = 0;
                return "Verify Successfully!!";
            } else {
                throw new IdNotFound("OTP has wrong please Try Again!!");
            }
        } else {
            throw new IdNotFound("OTP Time has Expired!!!");
        }
    }

    @Override
    public Integer generateOTP() {
        Integer max = 999999;
        Integer min = 100000;
        otp = (int) (Math.random() * (max - min + 1) + min);
        return otp;
    }
}