package com.ilyascan.taskflowapi.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskUpdateRequest {

    @NotBlank(message = "Task id'si boş olamaz")
    @Size(min = 2)
    private String id;

    @Size(min = 5, max = 150, message = "Task başlığı 5 ile 150 karakter arasında bir değer alabilir")
    private String taskTitle;

    @Size(min = 10, max = 700, message = "Task acıklaması 10 ile 700 karakter arasında bir değer alabilir")
    private String taskDescription;


    private Date taskEndTime;


}
