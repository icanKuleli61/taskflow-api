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
public class BoardDto {

    @NotBlank(message = "Calışma alanı adı boş olamaz")
    @Size(min = 3, max = 60, message = "Calışma alanı ismi 3 ile 60 karakter arasında olması lazım")
    private String boardName;

    @NotBlank(message = "Calışma alanı oluştururken userId boş olmamalı")
    @Size(min = 3, message = "User id yanlış")
    private String userId;

    @NotBlank(message = "Calışma alanı açıklaması boş olmamalı")
    @Size(min = 5, max = 800, message = "Calışma alanı ismi 5 ile 800 karakter arasında olması lazım")
    private String boardDescription;

}
