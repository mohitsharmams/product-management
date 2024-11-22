package com.company.exception.handler;

import com.company.enums.ErrorCodes;
import com.company.exception.ResourceNotFoundException;
import com.company.jpa.dto.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.builder().errorCode(ex.getErrorCode()).message(ex.getMessage()).build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        var errorMessage = new StringBuilder();
        for (var error : allErrors) {
            errorMessage.append(error.getDefaultMessage());
            if (allErrors.size() > 1 && allErrors.indexOf(error) != allErrors.size() - 1) {
                errorMessage.append(" | ");
            }
        }
        return ResponseEntity.badRequest().body(
                ErrorResponse.builder().message(errorMessage.toString()).errorCode(ErrorCodes.CONSTRAINT_VIOLATION).build()
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse.builder()
                        .message("Incorrect username or password")
                        .errorCode(ErrorCodes.BAD_CREDENTIALS).build());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder().message(ex.getMessage()).errorCode(ErrorCodes.INTERNAL_SERVER_ERROR).build());
    }
}