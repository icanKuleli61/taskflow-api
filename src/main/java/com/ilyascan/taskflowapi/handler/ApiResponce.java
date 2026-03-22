package com.ilyascan.taskflowapi.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponce<T>{

    public boolean success;

    public String message;

    public T data;

    public ExceptionResponce error;

    public Date timestamp ;


}
