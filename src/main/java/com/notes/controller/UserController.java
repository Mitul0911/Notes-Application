package com.notes.controller;

import com.notes.dto.RequestDto;
import com.notes.response.Response;
import com.notes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public Response createNewUser(@RequestBody RequestDto dto) {
        return userService.registerUser(dto);
    }

    @PostMapping("/login")
    public Response login(@RequestBody RequestDto dto) {
        return userService.checkLoginCredentials(dto);
    }

}
