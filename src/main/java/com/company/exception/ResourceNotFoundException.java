package com.company.exception;

import com.company.enums.ErrorCodes;
import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {

    private final ErrorCodes errorCode;

    public ResourceNotFoundException(ErrorCodes errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}