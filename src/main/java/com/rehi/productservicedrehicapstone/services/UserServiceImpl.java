package com.rehi.productservicedrehicapstone.services;

import com.rehi.productservicedrehicapstone.dtos.*;
import com.rehi.productservicedrehicapstone.exceptions.InvalidCredentialsException;
import com.rehi.productservicedrehicapstone.exceptions.UserNotFoundException;
import com.rehi.productservicedrehicapstone.models.Role;
import com.rehi.productservicedrehicapstone.models.User;
import com.rehi.productservicedrehicapstone.repositories.RoleRepository;
import com.rehi.productservicedrehicapstone.repositories.UserRepository;
import com.rehi.productservicedrehicapstone.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public AuthResponseDto register(UserRegistrationDto registrationDto) {
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new InvalidCredentialsException("Email is already in use");
        }

        String encodedPassword = passwordEncoder.encode(registrationDto.getPassword());

        Role customerRole = roleRepository.findByRoleName("CUSTOMER")
                .orElseGet(() -> roleRepository.save(Role.builder().roleName("CUSTOMER").build()));

        Set<Role> roles = new HashSet<>();
        roles.add(customerRole);

        User user = User.builder()
                .email(registrationDto.getEmail())
                .password(encodedPassword)
                .firstName(registrationDto.getFirstName())
                .lastName(registrationDto.getLastName())
                .mobileNumber(registrationDto.getMobileNumber())
                .roles(roles)
                .build();

        user = userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail());

        return AuthResponseDto.builder()
                .jwtToken(token)
                .userId(user.getUserId())
                .username(user.getEmail())
                .build();
    }

    @Override
    public AuthResponseDto login(LoginRequestDto loginRequestDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDto.getEmail(),
                            loginRequestDto.getPassword())
            );
        } catch (AuthenticationException ex) {
            log.warn("Invalid login attempt for email {}", loginRequestDto.getEmail());
            throw new InvalidCredentialsException("Invalid email or password");
        }

        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + loginRequestDto.getEmail()));

        String token = jwtUtil.generateToken(user.getEmail());

        return AuthResponseDto.builder()
                .jwtToken(token)
                .userId(user.getUserId())
                .username(user.getEmail())
                .build();
    }

    @Override
    public UserProfileDto getCurrentUserProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        return UserProfileDto.from(user);
    }

    @Override
    @Transactional
    public UserProfileDto updateCurrentUserProfile(String email, UserProfileDto profileDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        // Only allow updating non-sensitive profile fields.
        user.setFirstName(profileDto.getFirstName());
        user.setLastName(profileDto.getLastName());
        user.setMobileNumber(profileDto.getMobileNumber());

        User saved = userRepository.save(user);

        return UserProfileDto.from(saved);
    }

    @Override
    @Transactional
    public void resetPassword(String email, PasswordResetDto passwordResetDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        if (!passwordEncoder.matches(passwordResetDto.getOldPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(passwordResetDto.getNewPassword()));
        userRepository.save(user);
    }
}


