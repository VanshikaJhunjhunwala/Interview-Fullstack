package com.example.interview.repository;

import com.example.interview.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Long> {
    List<Question> findByTopicAndDifficulty(String topic, String difficulty);
    Question findFirstByTopicAndDifficulty(String topic,String difficulty);
    boolean existsByQuestionText(String questionText);
}
