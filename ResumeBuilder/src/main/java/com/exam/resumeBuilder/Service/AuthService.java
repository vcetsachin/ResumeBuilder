package com.exam.resumeBuilder.Service;

import com.exam.resumeBuilder.Document.User;
import com.exam.resumeBuilder.Repository.UserRepository;
import com.exam.resumeBuilder.dto.AuthResponse;
import com.exam.resumeBuilder.dto.LoginRequest;
import com.exam.resumeBuilder.dto.RegisterRequest;
import com.exam.resumeBuilder.exception.ResourceExistException;
import com.exam.resumeBuilder.util.JwtUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Data
@Slf4j
public class AuthService {

    private final UserRepository repository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Value("${app.base.url:http://localhost:8080}")
    private String appBaseUrl;


    public AuthResponse register(RegisterRequest request) {
        log.info("Inside AuthService: Register() {}", request);
        if (repository.existsByEmail(request.getEmail())) {
            throw new ResourceExistException("User already exists with this email.");
        }
        User user = toDocument(request);
        repository.save(user);

        //TODO: Send verification email
        SendVerificationEmail(user);


        return toResponse(user);
    }

    private void SendVerificationEmail(User user) {
        log.info("Inside authService - sendVerificationEmail: {}", user);
        try{
            String link = appBaseUrl+"/api/auth/verify-email?token="+user.getVerificationToken();
            String html =
                    "<div style='font-family:Arial, sans-serif; max-width:500px; margin:auto; padding:20px; border:1px solid #ddd; border-radius:8px;'>"

                            + "<h2 style='color:#333;'>Verify Your Email</h2>"

                            + "<p>Hello " + user.getName() + ",</p>"

                            + "<p>Please click the button below to activate your account.</p>"

                            + "<a href='" + link + "' "
                            + "style='display:inline-block; padding:10px 15px; background:#4CAF50; color:white; "
                            + "text-decoration:none; border-radius:5px;'>Verify Email</a>"

                            + "<p style='margin-top:20px;'>If the button doesn’t work, copy this link:</p>"
                            + "<p><a href='" + link + "'>" + link + "</a></p>"

                            + "<p style='color:gray; font-size:12px;'>This link expires in 24 hours.</p>"

                            + "</div>";
            emailService.sendHtmlEmail(user.getEmail(),"Verify your email", html);

        }
        catch (Exception e){
            log.info("Exception occur at sendVerificationEmail: {}",e.getMessage());
            throw new RuntimeException("Failed to send verification email"+e.getMessage());
        }
    }

    private AuthResponse toResponse(User user){
      return  AuthResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .profileImageUrl(user.getProfileImageUrl())
                .emailVerified(user.isEmailVerified())
              .subscriptionPlan(user.getSubscriptionPlan())
              .createdAt(user.getCreatedAt())
              .updatedAt(user.getUpdateAt())
                .build();
    }
    private User toDocument(RegisterRequest request){
       return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .profileImageUrl(request.getProfileImageUrl())
                .subscriptionPlan("Basic")
                .emailVerified(false)
                .verificationToken(UUID.randomUUID().toString())
                .verificationExpires(LocalDateTime.now().plusHours(24))
                .build();
    }
    public void verifyToken(String token){
        log.info("Inside authService verifyEmail: {}", token);

       User user = repository.findByVerificationToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or expire verification token"));
        if (user.getVerificationExpires() != null && user.getVerificationExpires().isBefore(LocalDateTime.now())){
            throw new RuntimeException("Verification token has expired . Please request new one.");
        }
        user.setEmailVerified(true);
        user.setVerificationToken(null);
        user.setVerificationExpires(null);
        repository.save(user);
    }
    public AuthResponse login(LoginRequest loginRequest){
       User ExistingUser = repository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid email or password"));
       if (!passwordEncoder.matches(loginRequest.getPassword(), ExistingUser.getPassword())){
           throw new UsernameNotFoundException("Invalid email or password");
       }
       if (!ExistingUser.isEmailVerified()){
           throw new RuntimeException("Please verify your email before logging in.");
       }
     String token = jwtUtil.generateToken(ExistingUser.getId());
       AuthResponse response = toResponse(ExistingUser);
       response.setToken(token);
       return response;
    }
    public void resendVerification(String email){
        //Step1: fetch the user account by email
        User user1 = repository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        // Step2: Check the email is verified
        if(user1.isEmailVerified()){
            throw new RuntimeException("Email is already verified");
        }

        //Step3: Set new verification token and expires time
        user1.setVerificationToken(UUID.randomUUID().toString());
        user1.setVerificationExpires(LocalDateTime.now().plusHours(24));

        //Step4: Update the user
        repository.save(user1);

        //Step5: Resend the verification email
        SendVerificationEmail(user1);
    }

    public AuthResponse getProfile(Object principalObject){
        User existingUser = (User) principalObject;
        return toResponse(existingUser);

    }
}
