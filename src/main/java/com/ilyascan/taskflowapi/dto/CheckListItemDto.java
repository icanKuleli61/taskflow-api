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
@Builder
public class CheckListItemDto {

    @NotBlank(message = "Check list item boş olamaz")
    @Size(min = 8, max = 50, message = "check itemlar 8 ile 50 karakter icermelidir.")
    private String text;

}
