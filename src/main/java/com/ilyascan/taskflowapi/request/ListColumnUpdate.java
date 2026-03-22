package com.ilyascan.taskflowapi.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListColumnUpdate {

    @NotBlank(message = "List adı dolu olmalıdır. ")
    @Size(min = 3,max = 30 , message = "List'te adı 3 ile 30 karakter arasında bir karakter icermelidir.")
    private String listName;


    @NotBlank(message = "list'te acıklaması dolu olmalıdır.")
    private String listDescription;


}
