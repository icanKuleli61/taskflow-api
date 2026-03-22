package com.ilyascan.taskflowapi.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardUserTransactions {

    @NotBlank(message = "Calışma Alanını secmelisiniz ıd vermelisiniz")
    private String boardId;

    @NotBlank(message = "Calışma alanına işlem yapmak istediğiniz kullanıcının email'i ni göndermelisiniz")
    @Email(message = "Email formatınızı kontrol ediniz.")
    private String email;

}
