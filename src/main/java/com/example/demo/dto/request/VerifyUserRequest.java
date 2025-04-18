package com.example.demo.dto.request;

import lombok.Data;

@Data
public class VerifyUserRequest {
    private String email;
    private String verificationCode;
}
