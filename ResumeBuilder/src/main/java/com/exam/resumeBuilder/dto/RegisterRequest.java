package com.exam.resumeBuilder.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {

    @Email(message = "Email should be valid.")
    @NotBlank(message = "Email is required.")
    private String email;
    @NotBlank(message = "Name is required.")
    @Size(min=2, max=15, message = "Name must be between the 2 & 15 characters.")
    private String name;
    @Size(min=5, max = 15, message = "Password must be between the 5 & 15 character.")
    @NotBlank(message = "Password is required.")
    private String password;
    private String profileImageUrl;
}
