package com.ilyascan.taskflowapi.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateChangePassword {

    @NotBlank(message = "Şifre boş olamaz")
    @Size(min = 8,max = 20,message = "Şifreniz 8 karakter ile 20 karakter uzunluğunda olması gerekir ")
    private String oldPassword;

    @NotBlank(message = "Şifre boş olamaz")
    @Size(min = 8,max = 20,message = "Şifreniz 8 karakter ile 20 karakter uzunluğunda olması gerekir ")
    private String newPassword;

    @NotBlank(message = "Şifre boş olamaz")
    @Size(min = 8,max = 20,message = "Şifreniz 8 karakter ile 20 karakter uzunluğunda olması gerekir ")
    private String newAgainPassword;

    @AssertTrue(message = "Şifreler birbiriyle aynı olmalıdır!")
    private boolean isPasswordChanged(){
        return newPassword.equals(newAgainPassword);
    }


}
