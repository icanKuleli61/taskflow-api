package com.ilyascan.taskflowapi.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public enum ExceptionError {
    TASK_NOT_FOUND("Task bulunamadı", HttpStatus.NOT_FOUND, 2001L),
    TASK_NOT_FOUND_USER("Kullanıcıya ait Task bulunamadı", HttpStatus.NOT_FOUND, 2001L),
    TASK_NOT_OWNED("Bu görev size ait değil", HttpStatus.FORBIDDEN, 2002L),
    USER_NOT_FOUND("Kullanıcı bulunamadı", HttpStatus.NOT_FOUND, 2003L),
    INVALID_PASSWORD("Mevcut şifreniz yanlış", HttpStatus.UNAUTHORIZED, 2004L),

    USER_NOT_FOUND_LOGIN("Kullanıcı login olmamış", HttpStatus.UNAUTHORIZED, 2005L),

    BOARD_USER_NOT_FOUND("Kullanıcıya ait bir calışma alanı bulunamadı.", HttpStatus.NOT_FOUND, 2005L),

    EMAIL_ALREADY_EXISTS("Bu e-posta adresi zaten kullanımda", HttpStatus.CONFLICT, 2005L),
    INVALID_REFRESH_TOKEN("Refresh token geçersiz veya süresi dolmuş", HttpStatus.UNAUTHORIZED, 2006L),
    BAD_CREDENTIALS("E-posta veya şifre hatalı", HttpStatus.UNAUTHORIZED, 2007L),

    UNAUTHORIZED("Yetkisiz erişim", HttpStatus.UNAUTHORIZED, 2011L),

    PASSWORDS_DO_NOT_MATCH("Yeni şifreler birbiriyle uyuşmuyor", HttpStatus.BAD_REQUEST, 2008L),
    SAME_PASSWORD_ERROR("Yeni şifre eskisiyle aynı olamaz", HttpStatus.BAD_REQUEST, 2009L),

    VALIDATION_ERROR("Girdiğiniz bilgileri kontrol ediniz", HttpStatus.BAD_REQUEST, 2010L),
    INTERNAL_SERVER_ERROR("Beklenmedik bir hata oluştu", HttpStatus.INTERNAL_SERVER_ERROR, 9999L),

    BOARD_NOT_FOUND("Calışma alanı bulunamadı", HttpStatus.NOT_FOUND, 2010L),
    USER_DELETE_BOARD_FALSE("Calışma alanını silmeye yetkiniz yoktur", HttpStatus.UNAUTHORIZED, 2010L),
    BOARD_ADD_USER_FOUND("Boarda bir kişi eklemeye yetkiniz yoktur", HttpStatus.UNAUTHORIZED, 2010L),

    BOARD_IS_USER("Eklemek istediğiniz Kullanıcı Calışma alanına eklenmiş bir daha ekleyemezsiniz", HttpStatus.UNAUTHORIZED, 2010L),
    BOARD_IS_NOT_USER("Calışma alanında böyle bir kullanıcı bulunamadı.", HttpStatus.UNAUTHORIZED, 2010L),
    BOARD_IS_NOT_LIST("Calışma alanının listesi getirilemedi", HttpStatus.UNAUTHORIZED, 2010L),

    LIST_IS_NOT_FOUND("List'te bulunamadı", HttpStatus.NOT_FOUND, 2010L),

    TASK_NOT_FOUND_CHECKITEM("Task icinde bu check item bulunamadı.", HttpStatus.NOT_FOUND, 2010L),


;


    private final String exceptionName;

    private final HttpStatus httpStatus;

    private final Long exceptionCode;

    ExceptionError(String exceptionName, HttpStatus httpStatus, Long exceptionCode) {
        this.exceptionName = exceptionName;
        this.httpStatus = httpStatus;
        this.exceptionCode = exceptionCode;
    }



}
