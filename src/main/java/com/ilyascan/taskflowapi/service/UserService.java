package com.ilyascan.taskflowapi.service;


import com.ilyascan.taskflowapi.request.UpdateChangePassword;
import com.ilyascan.taskflowapi.request.UserUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface UserService {

    ResponseEntity<?> getMe(Authentication authentication);

    ResponseEntity<?> updateUser(UserUpdateRequest userUpdateRequest, Authentication authentication);

    ResponseEntity<?> changePassword(UpdateChangePassword userUpdateRequest, Authentication authentication);

    ResponseEntity<?> deleteMe(Authentication authentication);

}
