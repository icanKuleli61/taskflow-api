package com.ilyascan.taskflowapi.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDeleteRequest {

    @NotBlank(message = "Board Silmek icin sileceğiniz calışma alanının id'si girilmelidir.")
    private String boardId;
}
