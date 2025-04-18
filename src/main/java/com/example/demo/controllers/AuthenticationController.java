package com.example.demo.controllers;

import com.example.demo.dto.request.AuthenticationRequest;
import com.example.demo.dto.response.AuthenticationResponse;
import com.example.demo.dto.request.VerifyUserRequest;
import com.example.demo.services.AuthenticationService;
import com.example.demo.dto.request.SignUpRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    //TODO сделать документацию через swagger
    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(service.signup(request));
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestBody VerifyUserRequest verifyUserDto) {
        return ResponseEntity.ok(service.verifyUser(verifyUserDto));
    }

    @PostMapping("/resend")
    public ResponseEntity<?>  resendVerificationCode(@RequestParam String email) {
        return ResponseEntity.ok(service.resendVerificationCode(email));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        service.refreshToken(request, response);
    }
}
