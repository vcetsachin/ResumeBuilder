package com.exam.resumeBuilder.Repository;

import com.exam.resumeBuilder.Document.Resume;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ResumeRepository extends MongoRepository<Resume, String> {
    List<Resume> findByUserIdOrderByUpdateAtDesc(String userId);
    Optional<Resume> findByUserIdAndId(String userId, String id);

}
