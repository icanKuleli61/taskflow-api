package com.ilyascan.taskflowapi.responce;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskResponce {

    private String taskTitle;

    private String taskDescription;

    private Date taskStartTime;

    private Date taskEndTime;

}
