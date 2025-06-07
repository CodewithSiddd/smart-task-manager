package com.codewithsidd.taskmanager.controller;

import com.codewithsidd.taskmanager.entity.User;
import com.codewithsidd.taskmanager.service.UserService;
import com.codewithsidd.taskmanager.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

//    public UserController(UserService userService) {
//        this.userService = userService;
//    }

    @GetMapping("/me")
    public ResponseEntity<User> getLoggedInUser(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        Map<String, Object> claims = JwtUtils.decodeToken(token); // helper to extract claims
        User user = userService.createOrUpdateFromTokenClaims(claims);
        return ResponseEntity.ok(user);
    }

}
