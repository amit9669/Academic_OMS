package com.academic.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
//@AllArgsConstructor
public class EntityResponse {

    private String message ;
    private Integer status ;

    public EntityResponse(String message,Integer status) {
        this.message = message ;
        this.status = status ;
    }
}
