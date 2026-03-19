package com.ilyascan.taskflowapi.responce;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGetMeResponce {

    private String username;

    private String firstName;

    private String surname;

    private String email;

}
