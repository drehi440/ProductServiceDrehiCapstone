package com.rehi.productservicedrehicapstone.services;

import com.rehi.productservicedrehicapstone.dtos.*;

public interface UserService {

    AuthResponseDto register(UserRegistrationDto registrationDto);

    AuthResponseDto login(LoginRequestDto loginRequestDto);

    UserProfileDto getCurrentUserProfile(String email);

    UserProfileDto updateCurrentUserProfile(String email, UserProfileDto profileDto);

    void resetPassword(String email, PasswordResetDto passwordResetDto);
}


