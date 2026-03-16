package com.ilyascan.taskflowapi.controller;

import com.ilyascan.taskflowapi.service.UserService;

public class UserControllerV1 {

    private final UserService userService;

    public UserControllerV1(UserService userService) {
        this.userService = userService;
    }



}
