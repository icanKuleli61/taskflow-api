package com.ilyascan.taskflowapi.service;

import com.ilyascan.taskflowapi.dto.UserDto;
import com.ilyascan.taskflowapi.request.AuthLoginRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {

     ResponseEntity<?> registerUser(UserDto userDto);

     ResponseEntity<?> login(AuthLoginRequest authLoginRequest);
}
