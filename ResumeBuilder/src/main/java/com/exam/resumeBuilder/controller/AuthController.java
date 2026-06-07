package com.exam.resumeBuilder.controller;

import com.exam.resumeBuilder.Document.User;
import com.exam.resumeBuilder.Service.AuthService;
import com.exam.resumeBuilder.Service.FileUploadService;
import com.exam.resumeBuilder.dto.AuthResponse;
import com.exam.resumeBuilder.dto.LoginRequest;
import com.exam.resumeBuilder.dto.RegisterRequest;
import jakarta.mail.Multipart;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.PublicKey;
import java.util.Map;
import java.util.Objects;

import static com.exam.resumeBuilder.util.AppConstans.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(AUTH_CONTROLLER)
public class AuthController {

    private final AuthService authService;
    private final FileUploadService fileUploadService;

    @PostMapping(REGISTER)
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request){
        log.info("Inside AuthController - register(): {}", request);
        AuthResponse authResponse = authService.register(request);
        log.info("Response from service: {}", authResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }

    @GetMapping(VERIFY_EMAIL)
    public ResponseEntity <?> verifyEmail(@RequestParam String token){
        log.info("Inside AuthController - verifyEmail(): {}", token);
        authService.verifyToken(token);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Verification is successfully."));
    }

    @PostMapping(UPLOAD_IMAGE)
    public ResponseEntity<?> uploadImage(@RequestParam("image")MultipartFile file) throws Exception{
        log.info("Inside AuthController - uploadImage()");
        Map<String, String> Response = fileUploadService.uploadSingleImage(file);
            //        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        return ResponseEntity.ok(Response);
        }
        @PostMapping("/login")
        public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest){
         AuthResponse response =  authService.login(loginRequest);
         return ResponseEntity.ok(response);
    }

    @GetMapping("/auth/validate")
    public String testValidationToken(){
        return "Token validation is the working";
    }

    @PostMapping(RESEND_VERIFICATION)
    public ResponseEntity<?> resendVerification(@RequestBody Map<String, String> body){

        // Step1: get the email from the request
        String email = body.get("email");

        //Step2: Add thw validations
        if(Objects.isNull(email)){
            return  ResponseEntity.badRequest().body(Map.of("message", "Email is required"));
        }

        //Step3: Call the service method to resend verification link
        authService.resendVerification(email);

        //Step4: Return response
        return ResponseEntity.ok(Map.of("success", true, "message", "verification email sent"));
    }

    @GetMapping(PROFILE)
    public ResponseEntity<?> getProfile(Authentication authentication){

        //Step1: Get the principle object
       Object principalObject =  authentication.getPrincipal();

        //Step2: call the service method
        AuthResponse currentProfile = authService.getProfile(principalObject);

        //Step3: return the response
        return ResponseEntity.ok(currentProfile);
    }

}
