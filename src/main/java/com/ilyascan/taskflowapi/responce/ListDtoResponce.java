package com.ilyascan.taskflowapi.responce;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListDtoResponce {

    private String id;

    private String listName;

    private String listDescription;

    private Integer taskCount;

    private List<TaskDtoResponce> tasks;
}
