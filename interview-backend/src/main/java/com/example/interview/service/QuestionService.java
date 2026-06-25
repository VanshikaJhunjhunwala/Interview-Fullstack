package com.example.interview.service;

import com.example.interview.dto.QuestionDTO;
import com.example.interview.entity.Question;
import com.example.interview.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class QuestionService {
    private final QuestionRepository repo;
    private final QuestionRepository questionRepo;
    public QuestionService(QuestionRepository repo,QuestionRepository questionRepo){
        this.repo=repo;
        this.questionRepo=questionRepo;
    }
    public Question addQuestion(QuestionDTO dto){
        boolean exists = repo.existsByQuestionText(dto.getQuestionText());
        System.out.println("Question = " + dto.getQuestionText());
        System.out.println("Exists = " + exists);
        if(repo.existsByQuestionText(dto.getQuestionText())){
            throw new RuntimeException("Question Already Exists");
        }
        Question q=new Question();
        q.setQuestionText(dto.getQuestionText());
        q.setTopic(dto.getTopic());
        q.setDifficulty(dto.getDifficulty());
        q.setExpectedAnswer(dto.getExpectedAnswer());
        return repo.save(q);
    }
    public List<Question> getQuestions(String topic, String difficulty){
        return repo.findByTopicAndDifficulty(topic, difficulty);
    }
    public Question getRandomQuestion(String topic, String difficulty) {
        List<Question> list = questionRepo.findAll();

        List<Question> filtered = list.stream()
                .filter(q ->
                        q.getTopic() != null &&
                                q.getDifficulty() != null &&
                                q.getTopic().trim().equalsIgnoreCase(topic.trim()) &&
                                q.getDifficulty().trim().equalsIgnoreCase(difficulty.trim())
                )
                .toList();

        if (filtered.isEmpty()) {
            throw new RuntimeException("No question found for given topic/difficulty");
        }

        int index = new java.util.Random().nextInt(filtered.size());

        return filtered.get(index);
    }
    public int getQuestionCount(String topic, String difficulty){
        return repo.findByTopicAndDifficulty(topic, difficulty).size();
    }
    public void deleteQuestion(Long id){
        repo.deleteById(id);
    }
    public Question updateQuestion(Long id, QuestionDTO dto){
        Question q = repo.findById(id).orElseThrow();
        q.setQuestionText(dto.getQuestionText());
        q.setTopic(dto.getTopic());
        q.setDifficulty(dto.getDifficulty());
        q.setExpectedAnswer(dto.getExpectedAnswer());
        return repo.save(q);
    }
    public Question getQuestionById(Long id){
        return repo.findById(id).orElseThrow();
    }
    public boolean questionExists(String questionText){
        return repo.existsByQuestionText(questionText);
    }
}
