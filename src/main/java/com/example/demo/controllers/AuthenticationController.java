package com.example.demo.controllers;

import com.example.demo.dto.request.AuthenticationRequest;
import com.example.demo.dto.request.ResendVerificationCodeRequest;
import com.example.demo.dto.response.AuthenticationResponse;
import com.example.demo.dto.request.VerifyUserRequest;
import com.example.demo.services.login.AuthenticationService;
import com.example.demo.dto.request.SignUpRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    //TODO сделать документацию через swagger
    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody SignUpRequest sign) {
        return ResponseEntity.ok(service.signup(sign));
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestBody VerifyUserRequest verifyUserDto) {
        return ResponseEntity.ok(service.verifyUser(verifyUserDto));
    }

    @PostMapping("/resend")
    public ResponseEntity<?> resendVerificationCode(@RequestBody ResendVerificationCodeRequest resend) {
        return ResponseEntity.ok(service.resendVerificationCode(resend));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authentication) {
        return ResponseEntity.ok(service.authenticate(authentication));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        service.refreshToken(request, response);
    }
}
