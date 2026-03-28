package com.ilyascan.taskflowapi.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckListItemUpdate {

    private String text;
}
