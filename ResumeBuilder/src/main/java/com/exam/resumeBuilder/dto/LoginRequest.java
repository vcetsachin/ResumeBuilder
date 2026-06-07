package com.exam.resumeBuilder.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "Email is required.")
    @Email(message = "Email should be valid.")
    private String email;

    @NotBlank(message = "Password id required.")
    private String password;
}
