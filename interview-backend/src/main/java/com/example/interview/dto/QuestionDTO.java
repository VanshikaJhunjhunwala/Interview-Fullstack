package com.example.interview.dto;

import jakarta.validation.constraints.NotBlank;

public class QuestionDTO {
    @NotBlank(message = "Question text is required")
    private String questionText;
    @NotBlank(message = "Topic is required")
    private String topic;
    @NotBlank(message = "Difficulty is required")
    private String difficulty;
    @NotBlank(message = "Expected answer is required")
    private String expectedAnswer;

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getExpectedAnswer() {
        return expectedAnswer;
    }

    public void setExpectedAnswer(String expectedAnswer) {
        this.expectedAnswer = expectedAnswer;
    }
}
