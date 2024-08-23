package com.example.MyPersonalContactManager.controller;

import com.example.MyPersonalContactManager.models.UserModels.UserDTOLogin;
import com.example.MyPersonalContactManager.models.UserModels.UserDTORegister;
import com.example.MyPersonalContactManager.models.UserModels.UserDTOResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping (value = "/userRegistration")
    public ResponseEntity<UserDTOResponse> userRegistration (UserDTORegister userDto) {
        return null;
    }

    @GetMapping("/users/authUser")
    public ResponseEntity<UserDTOResponse> userRegistration (UserDTOLogin userDto) {
        return null;
    }
}
