package com.ilyascan.taskflowapi.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExceptionResponce {

    public String exceptionName;

    public Long exceptionCode;

    public String path;

}
