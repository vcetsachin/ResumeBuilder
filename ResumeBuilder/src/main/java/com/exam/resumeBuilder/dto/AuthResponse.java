package com.exam.resumeBuilder.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
private String id;
private String name;
private String email;
private String profileImageUrl;
private boolean emailVerified;
private String token;
private LocalDateTime createdAt;
private LocalDateTime updatedAt;
public String subscriptionPlan;
}
