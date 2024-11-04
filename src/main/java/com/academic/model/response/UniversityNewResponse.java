package com.academic.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class UniversityNewResponse {

    private Long unvId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("alpha_two_code")
    private String code;

    @JsonProperty("state-province")
    private String stateProvince;

    @JsonProperty("country")
    private String country;

    @JsonProperty("web_pages")
    private List<String> webUrl;

    @JsonProperty("domains")
    private List<String> domains;
}
