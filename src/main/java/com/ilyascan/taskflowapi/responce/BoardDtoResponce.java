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
public class BoardDtoResponce {

    private String boardId;

    private String boardName;

    private String boardDescription;


    private Date createdAt;

    private List<ListDtoResponce> lists;

}
