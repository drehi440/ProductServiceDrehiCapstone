package com.rehi.productservicedrehicapstone.controllers;

import com.rehi.productservicedrehicapstone.dtos.UserProfileDto;
import com.rehi.productservicedrehicapstone.services.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserProfileDto> getProfile(java.security.Principal principal) {
        // Extract authenticated user identifier (email) from Principal, which is populated from the JWT subject.
        String email = principal.getName();
        log.info("Fetching profile for user {}", email);
        UserProfileDto profile = userService.getCurrentUserProfile(email);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @PutMapping("/profile")
    public ResponseEntity<UserProfileDto> updateProfile(@RequestBody UserProfileDto profileDto,
                                                        java.security.Principal principal) {
        String email = principal.getName();
        log.info("Updating profile for user {}", email);
        UserProfileDto updated = userService.updateCurrentUserProfile(email, profileDto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }
}


