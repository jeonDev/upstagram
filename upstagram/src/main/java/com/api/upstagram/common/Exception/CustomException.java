package com.api.upstagram.common.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomException extends RuntimeException{
    
    private String code;
    private String message;

}
