package com.ilyascan.taskflowapi.exception;

public class CustomException extends RuntimeException {


    private final ExceptionError exceptionError;


    public CustomException(ExceptionError exceptionError) {
       super(exceptionError.getExceptionName());
        this.exceptionError = exceptionError;
    }

    public ExceptionError getExceptionError() {
        return exceptionError;
    }

}
