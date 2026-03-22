package com.ilyascan.taskflowapi.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TaskRequest {

    @NotBlank(message = "Görmek istediğiniz taskların bağlı olduğu list'te ıd sini vermelisiniz.")
    private String listId;
}
