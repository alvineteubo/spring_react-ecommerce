
 package com.proxidevcode.spring_react_ecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
private String fieldName;
private Object fieldValue;
private String resourceName;

    public ResourceNotFoundException( String resourceName, String fieldName, Object fieldValue) {
    // category avec id: 22 n'existe pas
    super(String.format("%s with field %s : %s not found.", resourceName, fieldName, fieldValue));
    this.fieldName = fieldName;
    this.fieldValue = fieldValue;
    this.resourceName = resourceName;
}

   


    
}