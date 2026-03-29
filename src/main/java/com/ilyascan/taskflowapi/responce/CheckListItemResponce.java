package com.ilyascan.taskflowapi.responce;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckListItemResponce {

    private String id;

    private String text;

    private boolean completed;

    private Integer position;
}
