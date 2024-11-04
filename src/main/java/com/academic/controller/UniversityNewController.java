package com.academic.controller;

import com.academic.model.UniversityNew;
import com.academic.model.response.UniversityNewResponse;
import com.academic.service.IUniversityNewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/universityNew")
public class UniversityNewController {

    @Autowired
    private IUniversityNewService iUniversityNewService;

    @GetMapping(value = "/country", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UniversityNewResponse[] getUniversityByCountry(@RequestParam String country) {

        UniversityNewResponse[] universityNew;
        universityNew = iUniversityNewService.fetchUniversityByCountry(country);
        return universityNew;
    }

    @PostMapping("/saveOrUpdateUniversityNew")
    public ResponseEntity<?> saveOrUpdate(@RequestBody List<UniversityNewResponse> universityNewResponse){
        return new ResponseEntity<>(iUniversityNewService.saveOrUpdate(universityNewResponse), HttpStatus.OK);
    }
}
