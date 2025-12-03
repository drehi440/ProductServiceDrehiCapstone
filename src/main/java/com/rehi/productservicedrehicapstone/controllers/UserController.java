package com.rehi.productservicedrehicapstone.controllers;

import com.rehi.productservicedrehicapstone.dtos.UserBioRequestDto;
import com.rehi.productservicedrehicapstone.dtos.UserProfileDto;
import com.rehi.productservicedrehicapstone.services.UserService;
import com.rehi.productservicedrehicapstone.services.ai.UserAiService;
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
    private final UserAiService userAiService;

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

    /**
     * POST /api/users/profile/ai-bio
     * Generates a short, AI-powered bio suggestion based on the user's interests.
     */
    @PostMapping("/profile/ai-bio")
    public ResponseEntity<String> generateAiBio(@RequestBody UserBioRequestDto request) {
        log.info("Generating AI bio for interests='{}'", request.getInterests());
        String bio = userAiService.generateUserBio(request.getInterests());
        return new ResponseEntity<>(bio, HttpStatus.OK);
    }
}

