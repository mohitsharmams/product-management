package com.company.jpa.dto;

import com.company.enums.ErrorCodes;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class ErrorResponse {

    private final ErrorCodes errorCode;

    private final String message;

}
