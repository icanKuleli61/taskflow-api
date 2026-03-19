package com.ilyascan.taskflowapi.controller;

import com.ilyascan.taskflowapi.request.UpdateChangePassword;
import com.ilyascan.taskflowapi.request.UserUpdateRequest;
import com.ilyascan.taskflowapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserControllerV1 {

    private final UserService userService;

    public UserControllerV1(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/me")
    public ResponseEntity<?> getMe(Authentication authentication) {
        return userService.getMe(authentication);
    }

    @PutMapping("/me")
    public ResponseEntity<?> updateUser(@RequestBody @Valid UserUpdateRequest userUpdateRequest ,
                                        Authentication authentication) {

        return userService.updateUser(userUpdateRequest,authentication);
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody @Valid UpdateChangePassword  userUpdateRequest
                                            ,Authentication authentication){
        return userService.changePassword(userUpdateRequest,authentication);
    }

    @DeleteMapping("/me")
    public ResponseEntity<?> deleteMe(Authentication authentication){
        return userService.deleteMe(authentication);
    }




}
