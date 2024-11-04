package com.academic.service;

import com.academic.model.response.UniversityNewResponse;

import java.util.List;

public interface IUniversityNewService {

    public UniversityNewResponse[] fetchUniversityByCountry(String country);

    public Object saveOrUpdate(List<UniversityNewResponse> universityNewResponse);
}
