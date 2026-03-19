package com.ilyascan.taskflowapi.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public enum ExceptionError {
    TASK_NOT_FOUND("Task bulunamadı", HttpStatus.NOT_FOUND, 2001L),
    TASK_NOT_OWNED("Bu görev size ait değil", HttpStatus.FORBIDDEN, 2002L),
    USER_NOT_FOUND("Kullanıcı bulunamadı", HttpStatus.NOT_FOUND, 2003L),
    INVALID_PASSWORD("Mevcut şifreniz yanlış", HttpStatus.UNAUTHORIZED, 2004L),

    EMAIL_ALREADY_EXISTS("Bu e-posta adresi zaten kullanımda", HttpStatus.CONFLICT, 2005L),
    INVALID_REFRESH_TOKEN("Refresh token geçersiz veya süresi dolmuş", HttpStatus.UNAUTHORIZED, 2006L),
    BAD_CREDENTIALS("E-posta veya şifre hatalı", HttpStatus.UNAUTHORIZED, 2007L),

    PASSWORDS_DO_NOT_MATCH("Yeni şifreler birbiriyle uyuşmuyor", HttpStatus.BAD_REQUEST, 2008L),
    SAME_PASSWORD_ERROR("Yeni şifre eskisiyle aynı olamaz", HttpStatus.BAD_REQUEST, 2009L),

    VALIDATION_ERROR("Girdiğiniz bilgileri kontrol ediniz", HttpStatus.BAD_REQUEST, 2010L),
    INTERNAL_SERVER_ERROR("Beklenmedik bir hata oluştu", HttpStatus.INTERNAL_SERVER_ERROR, 9999L);

    private final String exceptionName;

    private final HttpStatus httpStatus;

    private final Long exceptionCode;

    ExceptionError(String exceptionName, HttpStatus httpStatus, Long exceptionCode) {
        this.exceptionName = exceptionName;
        this.httpStatus = httpStatus;
        this.exceptionCode = exceptionCode;
    }



}
