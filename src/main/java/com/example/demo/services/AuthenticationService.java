package com.example.demo.services;

import com.example.demo.dto.request.AuthenticationRequest;
import com.example.demo.dto.response.AuthenticationResponse;
import com.example.demo.dto.request.SignUpRequest;
import com.example.demo.dto.request.VerifyUserRequest;
import com.example.demo.exceptions.ValidationException;
import com.example.demo.models.MyUser;
import com.example.demo.repositories.MyUserRepository;
import com.example.demo.config.JwtService;
import com.example.demo.models.Token;
import com.example.demo.repositories.TokenRepository;
import com.example.demo.enums.TokenType;
import com.example.demo.util.CodeGenerator;
import com.example.demo.util.EmailTemplateBuilder;
import com.example.demo.validation.RequestValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final MyUserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final RequestValidator validator;

    public AuthenticationResponse signup(SignUpRequest request) {
        validator.signUpValidate(request);

        log.info("Registering new user with email: {}", request.getEmail());

        var user = MyUser.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .verificationCode(CodeGenerator.generateVerificationCode())
                .verificationCodeExpiresAt(LocalDateTime.now().plusMinutes(15))
                .enabled(false)
                .build();

        sendVerificationEmail(user);

        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(MyUser user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(MyUser user) {
        var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());

        if (validUserTokens.isEmpty()) {
            return;
        }

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow();

            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    private void sendVerificationEmail(MyUser user) { //TODO: Update with company logo
        String subject = "Account Verification";
        String verificationCode = "VERIFICATION CODE " + user.getVerificationCode();
        String htmlMessage = EmailTemplateBuilder.buildVerificationEmail(verificationCode);

        try {
            emailService.sendVerificationEmail(user.getEmail(), subject, htmlMessage);
        } catch (MessagingException cause) {
            log.error("The MessagingException occurred while sending the message: {}", cause.getMessage());
        } catch (Exception cause) {
            log.error("The Exception occurred while sending the message: {}", cause.getMessage());
        }
    }

    public Map<String, Object> verifyUser(VerifyUserRequest input) {
        Optional<MyUser> optionalUser = repository.findByEmail(input.getEmail());
        Map<String, List<String>> errors = new HashMap<>();

        if (optionalUser.isEmpty()) {
            errors.put("email", List.of("User not found"));
            throw new ValidationException(errors);
        }

        MyUser user = optionalUser.get();

        if (user.getVerificationCodeExpiresAt().isBefore(LocalDateTime.now())) {
            errors.put("verificationCode", List.of("Verification code has expired"));
            throw new ValidationException(errors);
        } else if (!user.getVerificationCode().equals(input.getVerificationCode())) {
            errors.put("verificationCode", List.of("Invalid verification code"));
            throw new ValidationException(errors);
        }

        user.setEnabled(true);
        user.setVerificationCode(null);
        user.setVerificationCodeExpiresAt(null);
        repository.save(user);

        Map<String, Object> response = new HashMap<>();
        response.put("messages", Map.of("success", List.of("Account verified successfully")));
        response.put("status", "success");
        response.put("code", 200);
        return response;
    }

    public Map<String, Object> resendVerificationCode(String email) {
        Optional<MyUser> optionalUser = repository.findByEmail(email);
        Map<String, List<String>> errors = new HashMap<>();

        if (optionalUser.isEmpty()) {
            errors.put("email", List.of("User not found"));
            throw new ValidationException(errors);
        }

        MyUser user = optionalUser.get();

        if (user.isEnabled()) {
            errors.put("email", List.of("Account is already verified"));
            throw new ValidationException(errors);
        }

        user.setVerificationCode(CodeGenerator.generateVerificationCode());
        user.setVerificationCodeExpiresAt(LocalDateTime.now().plusHours(1));
        sendVerificationEmail(user);
        repository.save(user);

        Map<String, Object> response = new HashMap<>();
        response.put("messages", Map.of("success", List.of("Verification code sent successfully")));
        response.put("status", "success");
        response.put("code", 200);
        return response;
    }
}
