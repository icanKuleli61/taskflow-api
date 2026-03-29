package com.ilyascan.taskflowapi.responce;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDtoResponce {


    private String taskId;

    private String taskTitle;

    private String taskDescription;

    private Date startTime;

    private Date endTime;

    private Integer checklistTotal;

    private Integer checklistCompleted;

    private List<CheckListItemResponce> checklist;
}
