package com.academic.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogInResponse {

    private String jwtToken;
}
