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
public class AuthLoginRequest {

    @NotBlank(message = "Email alanı boş olamaz")
    @Email(message = "Girdiğiniz email formatına uyması gerekiyor.")
    public String email;

    @NotBlank(message = "Şifre boş olamaz")
    @Size(min = 8,max = 20,message = "Şifreniz 8 karakter ile 20 karakter uzunluğunda olması gerekir ")
    public String password;
}
