package com.rehi.productservicedrehicapstone.controllers;

import com.rehi.productservicedrehicapstone.dtos.*;
import com.rehi.productservicedrehicapstone.services.AuthService;
import com.rehi.productservicedrehicapstone.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody UserRegistrationDto registrationDto) {
        log.info("Registering new user with email {}", registrationDto.getEmail());
        AuthResponseDto response = userService.register(registrationDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        log.info("User login attempt for email {}", loginRequestDto.getEmail());
        AuthResponseDto response = userService.login(loginRequestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody PasswordResetDto passwordResetDto,
                                              java.security.Principal principal) {
        // Principal comes from the JWT-authenticated security context.
        String email = principal.getName();
        userService.resetPassword(email, passwordResetDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * POST /api/auth/logout
     * Blacklists the current JWT so it cannot be used again.
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String token = authHeader.substring(7);
        log.info("Logging out current session");
        authService.logout(token);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

