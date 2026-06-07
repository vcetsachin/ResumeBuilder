package com.exam.resumeBuilder.controller;

import com.exam.resumeBuilder.Document.Resume;
import com.exam.resumeBuilder.Service.FileUploadService;
import com.exam.resumeBuilder.Service.ResumeService;
import com.exam.resumeBuilder.dto.CreateResumeRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.exam.resumeBuilder.util.AppConstans.*;

@RestController
@RequestMapping(RESUME_CONTROLLER)
@RequiredArgsConstructor
@Slf4j
public class ResumeController {

    private final ResumeService resumeService;
    private final FileUploadService fileUploadService;

    @PostMapping
    public ResponseEntity<?> createResume(@Valid @RequestBody CreateResumeRequest request,
                                           Authentication authentication){
        //Step1: Call the service method
        Resume newResume = resumeService.creatResume(request, authentication.getPrincipal());

        //Step2: return response
        return ResponseEntity.status(HttpStatus.CREATED).body(newResume);
    }

    @GetMapping
    public ResponseEntity<?> getUserResume(Authentication authentication){

        // Step1: Call the service method
       List<Resume> resumes = resumeService.getUserResume(authentication.getPrincipal());

        // Step2: Return the response
        return ResponseEntity.ok(resumes);
    }
    @GetMapping(ID)
    public ResponseEntity<?> getUserResumeById(@PathVariable String id, Authentication authentication){

        // Step1: Call the service method
       Resume existingResume = resumeService.getResumeById(id, authentication.getPrincipal());

        //return the response
        return ResponseEntity.ok(existingResume);
    }

    @PutMapping(ID)
    public ResponseEntity<?> updateResume(@PathVariable String id,
                                          @RequestBody Resume updatedData,
                                          Authentication authentication) {
        // Step1: call the service method
        Resume updatedResume = resumeService.updateResume(id,updatedData,authentication.getPrincipal());

        // Step2: return the response
        return ResponseEntity.ok(updatedResume);

    }

    @PutMapping(getUploadImage)
    public ResponseEntity<?> UploadImages(@PathVariable String id,
                                                @RequestPart(value = "thumbnail", required = true)MultipartFile thumbnail,
                                                @RequestPart (value = "profileImage", required = false) MultipartFile profileImage,
                                                HttpServletRequest request, Authentication authentication) throws IOException {
        // Step1: call the service method
        Map<String, String> response = fileUploadService.uploadResumeImage(id, authentication.getPrincipal(), thumbnail, profileImage);

        // Step2: return the response
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(ID)
    public ResponseEntity<?> deletedResume(@PathVariable String id, Authentication authentication   ){

        //step1: call the service method
        resumeService.deleteResume(id, authentication.getPrincipal());
        return  ResponseEntity.ok(Map.of("message", "Resume deleted successfully."));

        //step2: return response
    }


}

