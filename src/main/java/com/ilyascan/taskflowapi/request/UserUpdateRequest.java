package com.ilyascan.taskflowapi.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {

    @NotBlank(message = "Kullanıcı adı boş olamaz")
    @Size(min = 3, max = 20,message = "Kullanıcı adınız en fazla 10 karakter uzunluğunda olabilir")
    private String username;

    @NotBlank(message = "İsminiz boş olamaz.")
    @Size(min = 3, max = 20,message = "isminiz en fazla 20 karakter uzunluğunda olmalıdır.")
    private String firstName;

    @NotBlank(message = "Soyadınız boş olamaz.")
    @Size(min = 3, max = 15,message = "Soyadınız en fazla 15 karakter uzunluğunda olmalıdır.")
    private String surname;

    @NotBlank(message = "Kullanıcı adı boş olamaz")
    @Email(message = "Girdiğiniz email, email formatın'da olmalı.")
    private String email;
}
