package com.academic.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IdNotFound extends RuntimeException {

    String message ;

}
