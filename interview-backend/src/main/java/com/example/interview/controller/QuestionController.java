package com.example.interview.controller;

import com.example.interview.dto.QuestionDTO;
import com.example.interview.entity.Question;
import com.example.interview.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionService service;
    public QuestionController(QuestionService service){
        this.service=service;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Question> getQuestions(@RequestParam String topic, @RequestParam String difficulty){
        return service.getQuestions(topic,difficulty);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> addQuestion(@Valid @RequestBody QuestionDTO dto){

        try{
            return ResponseEntity.ok(service.addQuestion(dto));
        }
        catch(RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/random")
    public Question getRandomQuestion(@RequestParam String topic,@RequestParam String difficulty) {
        return service.getRandomQuestion(topic, difficulty);
    }
    @GetMapping("/count")
    public int getQuestionCount(@RequestParam String topic,@RequestParam String difficulty){
        return service.getQuestionCount(topic, difficulty);
    }
    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable Long id){
        service.deleteQuestion(id);
    }
    @PutMapping("/{id}")
    public Question updateQuestion(@PathVariable Long id,@RequestBody QuestionDTO dto){
        return service.updateQuestion(id, dto);
    }
    @GetMapping("/{id}")
    public Question getQuestionById(@PathVariable Long id){
        return service.getQuestionById(id);
    }
    @GetMapping("/exists")
    public boolean questionExists(@RequestParam String questionText){
        return service.questionExists(questionText);
    }
}
