package com.ilyascan.taskflowapi.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenResponce {

    @NotBlank(message = "refresh token gönderiniz")
    private String refreshToken;
}
