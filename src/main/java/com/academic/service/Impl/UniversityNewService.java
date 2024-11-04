package com.academic.service.Impl;

import com.academic.exception.IdNotFound;
import com.academic.model.UniversityNew;
import com.academic.model.response.UniversityNewResponse;
import com.academic.repository.UniversityNewRepository;
import com.academic.service.IUniversityNewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
public class UniversityNewService implements IUniversityNewService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private UniversityNewRepository universityNewRepository;

    @Override
    public UniversityNewResponse[] fetchUniversityByCountry(String countryValue) {

        String url = "http://universities.hipolabs.com/search?country={country}";
        String url1 = url.replace("{country}", countryValue);
        System.out.println("URl is : " + url1);

        ResponseEntity<UniversityNewResponse[]> universityResponseEntity = restTemplate.getForEntity(url1, UniversityNewResponse[].class);

        int number = 0;
        System.out.println("Response status code : " + universityResponseEntity.getStatusCodeValue());
        UniversityNewResponse[] bodyArray = universityResponseEntity.getBody();
        if (bodyArray != null && bodyArray.length > 0) {
            for (UniversityNewResponse universityNew : bodyArray) {
                System.out.println((number++) + ")-->" + universityNew.getName() + "--->" + universityNew.getCountry() + "--->" + universityNew.getCode());
            }
            return bodyArray;
        } else {
            throw new IdNotFound("No universities found for the country: " + countryValue);
        }
    }

    @Override
    public Object saveOrUpdate(List<UniversityNewResponse> universityNewResponseList) {

        for (UniversityNewResponse uniResponse : universityNewResponseList) {
            String domain = "";
            String webUrl = "";
            if (universityNewRepository.existsById(uniResponse.getUnvId())) {
                UniversityNew universityNew1 = universityNewRepository.findById(uniResponse.getUnvId()).get();
                universityNew1.setCountry(uniResponse.getCountry());
                universityNew1.setCode(uniResponse.getCode());
                universityNew1.setStateProvince(uniResponse.getStateProvince());
                universityNew1.setName(uniResponse.getName());

                for (int i = 0 ; i < uniResponse.getDomains().size(); i++){
                    domain = uniResponse.getDomains().size() >= 2 ? (uniResponse.getDomains().get(i) + ", " + domain) : (uniResponse.getDomains().get(i));
                }
                for (int i = 0 ; i < uniResponse.getWebUrl().size(); i++){
                    webUrl = uniResponse.getWebUrl().size() >= 2 ? (uniResponse.getWebUrl().get(i) + ", " + webUrl) : (uniResponse.getWebUrl().get(i));
                }
                System.out.println(domain);
                System.out.println(webUrl);
                universityNew1.setDomains(domain);
                universityNew1.setWebUrl(webUrl);
                universityNewRepository.save(universityNew1);
            } else {
                UniversityNew universityNew = new UniversityNew();
                universityNew.setCountry(uniResponse.getCountry());
                universityNew.setCode(uniResponse.getCode());
                universityNew.setStateProvince(uniResponse.getStateProvince());
                universityNew.setName(uniResponse.getName());

                for (int i = 0 ; i < uniResponse.getDomains().size(); i++){
                    domain = uniResponse.getDomains().size() >= 2 ? (uniResponse.getDomains().get(i) + ", " + domain) : (uniResponse.getDomains().get(i));
                }
                for (int i = 0 ; i < uniResponse.getWebUrl().size(); i++){
                    webUrl = uniResponse.getWebUrl().size() >= 2 ? (uniResponse.getWebUrl().get(i) + ", " + webUrl) : (uniResponse.getWebUrl().get(i));
                }
                System.out.println(domain);
                System.out.println(webUrl);
                domain = domain. replaceAll(", $", " ");
                webUrl = webUrl. replaceAll(", $", " ");
                universityNew.setDomains(domain);
                universityNew.setWebUrl(webUrl);
                universityNewRepository.save(universityNew);
            }
        }
        return "Thank-You!!!";
    }
}
