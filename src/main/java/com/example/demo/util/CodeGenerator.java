package com.example.demo.util;

import java.util.Random;

public class CodeGenerator {
    private CodeGenerator() {}

    public static String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000; // от 100000 до 999999
        return String.valueOf(code);
    }
}
