package com.ilyascan.taskflowapi.handler;

import com.ilyascan.taskflowapi.exception.CustomException;
import com.ilyascan.taskflowapi.exception.ExceptionError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponce<?>> customExceptionHandler(HttpServletRequest request
            , CustomException customException) {
        String servletPath = request.getServletPath();
        HttpStatus status =  customException.getExceptionError().getHttpStatus();
        ApiResponce<Object> apiReponce = toApiReponce(servletPath, customException);
        return new ResponseEntity<>(apiReponce, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponce<Object>> handleValidationExceptions(MethodArgumentNotValidException ex,
                                                                          HttpServletRequest request) {
        Map<String,String> erros = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            erros.put(error.getObjectName(), error.getDefaultMessage());
        });
        String servletPath = request.getServletPath();

        ExceptionError exceptionError = ExceptionError.VALIDATION_ERROR;

        ExceptionResponce  exceptionResponce= ExceptionResponce.builder()
                .exceptionName(exceptionError.getExceptionName())
                .exceptionCode(exceptionError.getExceptionCode())
                .path(servletPath)
                .build();

        ApiResponce<Object> apiResponce = ApiResponce.builder()
                .success(false)
                .data(erros)
                .error(exceptionResponce)
                .timestamp(new Date())
                .build();

        return new ResponseEntity<>(apiResponce, exceptionError.getHttpStatus());
    }

    private <T> ApiResponce<T> toApiReponce(String path, CustomException customException){
        ExceptionResponce exceptionResponse = toExceptionResponse(path, customException);
        return ApiResponce.<T>builder()
                .success(false)
                .error(exceptionResponse)
                .timestamp(new Date())
                .build();

    }

    private ExceptionResponce toExceptionResponse(String path, CustomException customException) {
        return ExceptionResponce.builder()
                .exceptionName(customException.getExceptionError().getExceptionName())
                .exceptionCode(customException.getExceptionError().getExceptionCode())
                .path(path)
                .build();
    }

}

