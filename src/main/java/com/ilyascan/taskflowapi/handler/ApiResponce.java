package com.ilyascan.taskflowapi.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponce<T>{

    public Boolean success;

    public T data;

    public ExceptionResponce error;

    public Date timestamp ;


}
