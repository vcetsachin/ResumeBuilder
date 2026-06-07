package com.exam.resumeBuilder.Document;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "resumes")
public class Resume {
    @Id
    @JsonProperty("_id")
    private String id;
    private String userId;
    private String title;
    private String thumbnailLink;
    private Template template;
    private ProfileInfo profileInfo;
    private ContactInfo contactInfo;
    private List<WorkExperience> workExperiences;
    private List<Education> educations;
    private List<Skill> skills;
    private List<Project> projects;
    private List<Certification> certifications;
    private List<Language> languages;
    private List<String> interest;


    @CreatedDate
    private LocalDateTime ceratedAt;
    @LastModifiedDate
    private LocalDateTime updateAt;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Template{

    private String theme;
    private List<String> colorPallet;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public static class ProfileInfo{
   private String profilePreviewUrl;
   private String fullName;
   private String destination;
   private  String summary;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public static class ContactInfo{
    private String email;
    private String phone;
    private String location;
    private String linkIn;
    private String github;
    private String website;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public static class WorkExperience{
    private String company;
    private String role;
    private String startDate;
    private String endDate;
    private String description;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public static class Education{
    private String degree;
    private String institution;
    private String startDate;
    private String endDate;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public static class Skill{
    private String name;
    private Integer progress;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public static class Project{
    private String name;
    private String description;
    private String github;
    private String liveDemo;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public static class Certification{
    private String title;
    private String issuer;
    private String year;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public static class Language{
    private String name;
    private String progress;
}
}
