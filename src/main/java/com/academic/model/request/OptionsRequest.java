package com.academic.model.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class OptionsRequest {

    private Long optionId;
    private Long questionId;
    private String options;
}
