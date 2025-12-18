package com.bookmyshow.controller;

import com.bookmyshow.dto.ApiResponse;
import com.bookmyshow.dto.LoginRequest;
import com.bookmyshow.dto.SignupRequest;
import com.bookmyshow.model.UserLogin;
import com.bookmyshow.service.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserLogin>> signup(@RequestBody SignupRequest request) {
        try {
            UserLogin user = userAuthenticationService.registerNewUser(
                    request.getName(),
                    request.getEmail(),
                    request.getPhone(),
                    request.getPassword()
            );
            return ResponseEntity.ok(new ApiResponse<>(true, "User registered successfully", user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserLogin>> login(@RequestBody LoginRequest request) {
        try {
            UserLogin user = userAuthenticationService.authenticateUser(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(new ApiResponse<>(true, "Login successful", user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @PostMapping("/logout/{userId}")
    public ResponseEntity<ApiResponse<Void>> logout(@PathVariable String userId) {
        try {
            userAuthenticationService.logoutUser(userId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Logout successful", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

}
