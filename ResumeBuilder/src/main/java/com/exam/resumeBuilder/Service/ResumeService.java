package com.exam.resumeBuilder.Service;

import com.exam.resumeBuilder.Document.Resume;
import com.exam.resumeBuilder.Repository.ResumeRepository;
import com.exam.resumeBuilder.dto.AuthResponse;
import com.exam.resumeBuilder.dto.CreateResumeRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j

public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final AuthService authService;

    public Resume creatResume(CreateResumeRequest request, Object PrincipleObject) {
        //Step1: Create resume object
        Resume newResume = new Resume();

        //Step2: Get the current profile
        AuthResponse response = authService.getProfile(PrincipleObject);

        //Step3: Update the resume object
        newResume.setUserId(response.getId());
        newResume.setTitle(request.getTitle());

        //Step4: Set default data
        setDefaultResumeData(newResume);

        //Step5: save the resume data
        return resumeRepository.save(newResume);

    }

    private void setDefaultResumeData(Resume newResume) {
        newResume.setProfileInfo(new Resume.ProfileInfo());
        newResume.setContactInfo(new Resume.ContactInfo());
        newResume.setWorkExperiences(new ArrayList<>());
        newResume.setEducations(new ArrayList<>());
        newResume.setSkills(new ArrayList<>());
        newResume.setProjects(new ArrayList<>());
        newResume.setCertifications(new ArrayList<>());
        newResume.setLanguages(new ArrayList<>());
        newResume.setInterest(new ArrayList<>());
    }

    public List<Resume> getUserResume(@Nullable Object principal) {

        // Step1: get the current profile
        AuthResponse response = authService.getProfile(principal);

        // Step2: call the repository finder method
        List<Resume> resumes = resumeRepository.findByUserIdOrderByUpdateAtDesc(response.getId());

        // Step3: return response
        return resumes;
    }

    public Resume getResumeById(String resumeId, Object principal) {
        // Step1: get the current profile
        AuthResponse response = authService.getProfile(principal);

        //Step2: call the repository finder method
        Resume existingResume = resumeRepository.findByUserIdAndId(response.getId(), resumeId)
                .orElseThrow(() -> new RuntimeException("Resume not found."));

        //Step3: return result
        return existingResume;
    }

    public Resume updateResume(String resumeId, Resume updatedData,
                               @Nullable Object principal) {

        // Step1: get the current profile
        AuthResponse response = authService.getProfile(principal);

        // Step2: call the repository finder method
        Resume existingResume = resumeRepository.findByUserIdAndId(response.getId(), resumeId)
                .orElseThrow(() -> new RuntimeException("Resume not found"));

        // step3: update the new data
        existingResume.setTitle(updatedData.getTitle());
        existingResume.setThumbnailLink(updatedData.getThumbnailLink());
        existingResume.setTemplate(updatedData.getTemplate());

        existingResume.setProfileInfo(updatedData.getProfileInfo());
        existingResume.setContactInfo(updatedData.getContactInfo());

        existingResume.setWorkExperiences(updatedData.getWorkExperiences());
        existingResume.setEducations(updatedData.getEducations());
        existingResume.setSkills(updatedData.getSkills());
        existingResume.setProjects(updatedData.getProjects());

        existingResume.setCertifications(updatedData.getCertifications());
        existingResume.setLanguages(updatedData.getLanguages());
        existingResume.setInterest(updatedData.getInterest());
        
        // step4: update the details into database
        resumeRepository.save(existingResume);

        // step5: return result
        return existingResume;
    }

    public void deleteResume(String resumeId, Object principal) {

        //Step1: get the current profile
        AuthResponse response = authService.getProfile(principal);

        //Step2: call the repo finder method
        Resume existingResume = resumeRepository.findByUserIdAndId(response.getId(), resumeId)
                .orElseThrow(() -> new RuntimeException("Resume not found."));
        resumeRepository.delete(existingResume);

    }
}
