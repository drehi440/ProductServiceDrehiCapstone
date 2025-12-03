package com.rehi.productservicedrehicapstone.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegistrationDto {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String mobileNumber;
}


