package com.proxidevcode.spring_react_ecommerce.dtos;
import java.util.Set;
import java.util.Map;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorResponseApi {
    private  String message;
    private  Integer errorCode;
    private String error;
    private String ErrorDescription;
    private Set<String> validationErrors;
    private Map<String, String > errors;
    private LocalDateTime timestamp; 


    
}
