package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.UserResponse;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getMyInfo(@AuthenticationPrincipal User user) {
        UserResponse userResponse = userService.getMyInfo(user);
        return ResponseEntity.ok(ApiResponse.success(userResponse));
    }
}