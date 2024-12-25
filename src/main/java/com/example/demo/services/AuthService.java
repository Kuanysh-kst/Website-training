package com.example.demo.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AppService appService;
    private final Map<String, String> refreshStorage = new HashMap<>();
}
