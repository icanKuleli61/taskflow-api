package com.ilyascan.taskflowapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    @NotBlank(message = "Task başlığı boş olamaz")
    @Size(min = 5, max = 150, message = "Task başlığı 5 ile 150 karakter arasında bir değer alabilir")
    private String taskTitle;

    @NotBlank(message = "Task acıklaması boş olamaz")
    @Size(min = 10, max = 700, message = "Task acıklaması 10 ile 700 karakter arasında bir değer alabilir")
    private String taskDescription;

}
