package com.mh.userservice.controller;

import com.mh.userservice.dto.UserReqDto;
import com.mh.userservice.dto.UserResDto;
import com.mh.userservice.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final Environment environment;

    @GetMapping("/env")
    public String env(){
        return String.format("local.server.port =  %s",environment.getProperty("local.server.port"));
    }

    @PostMapping("/add-user")
    public ResponseEntity<UserResDto> user(@Valid @RequestBody UserReqDto userReqDto){
        UserResDto userResDto = userService.createUser(userReqDto);
        return ResponseEntity.ok(userResDto);
    }

    @GetMapping("/login")
    public void login(String email, String password, HttpServletResponse res) throws IOException {
        res.sendRedirect("/users/login?email="+email+"&password="+password);
    }


}
