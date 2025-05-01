package com.example.demo.exceptions.unauthorized;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class JwtException extends UnauthorizedException {
    public JwtException(Map<String, List<String>> errors) {
        super(errors);
    }
}
