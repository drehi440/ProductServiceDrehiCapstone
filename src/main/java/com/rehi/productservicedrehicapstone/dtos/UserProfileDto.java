package com.rehi.productservicedrehicapstone.dtos;

import com.rehi.productservicedrehicapstone.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileDto {

    private Long userId;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String email;

    /**
     * Creates a safe view of the user without exposing sensitive fields like password.
     */
    public static UserProfileDto from(User user) {
        return UserProfileDto.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .mobileNumber(user.getMobileNumber())
                .email(user.getEmail())
                .build();
    }
}


