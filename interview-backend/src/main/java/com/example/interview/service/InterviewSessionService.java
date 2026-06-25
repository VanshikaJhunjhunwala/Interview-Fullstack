package com.example.interview.service;

import com.example.interview.dto.InterviewSessionDTO;
import com.example.interview.dto.SummaryDTO;
import com.example.interview.dto.UserSummaryDTO;
import com.example.interview.entity.InterviewSession;
import com.example.interview.repository.InterviewSessionRepository;
import org.springframework.stereotype.Service;
import com.example.interview.entity.Question;
import com.example.interview.repository.QuestionRepository;
import com.example.interview.dto.DashboardDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InterviewSessionService {
    private final InterviewSessionRepository repo;
    private final QuestionRepository questionRepo;
    public InterviewSessionService(InterviewSessionRepository repo,QuestionRepository questionRepo){
        this.repo=repo;
        this.questionRepo=questionRepo;
    }
    public InterviewSession startInterview(String email, InterviewSessionDTO dto) {
        Question q = questionRepo.findById(dto.getQuestionId()).orElse(null);
        int score = 0;
                String expected = q.getExpectedAnswer() == null? "": q.getExpectedAnswer().toLowerCase();
                String answer = dto.getAnswer() == null? "": dto.getAnswer().toLowerCase();
                if (answer.isBlank()) {
                    score = 0;
                }
                else if (answer.contains(expected)) {
                    score = 100;
                }
                else if (answer.length() > 20) {
                    score = 60;
                }
                else {
                    score = 20;
                }
        InterviewSession temp = new InterviewSession();
        temp.setUserEmail(email);
        temp.setTopic(dto.getTopic().trim().toLowerCase());
        temp.setDifficulty(dto.getDifficulty().trim().toLowerCase());
        temp.setScore(score);
        return temp;
    }
    public List<InterviewSession> getMySessions(String email){
        return repo.findByUserEmail(email);
    }
    public DashboardDTO getDashboard(String email){
        List<InterviewSession> list= repo.findByUserEmail(email);
        DashboardDTO dto=new DashboardDTO();
        dto.setTotalInterviews(list.size());
        double avg= list.stream().mapToInt(InterviewSession::getScore).average().orElse(0);
        dto.setAverageScore(avg);
        if(avg == 100){
            dto.setPerformance("Excellent Performance");
            dto.setWeakArea("");
        }
        else if(avg >= 80){
            dto.setPerformance("Good Performance");
            dto.setWeakArea("Java needs improvement");
        }
        else{
            dto.setPerformance("Needs Improvement");
            dto.setWeakArea("");
        }
        return dto;
    }
    public SummaryDTO getSummary(String email){
        List<InterviewSession> list = repo.findByUserEmail(email);
        SummaryDTO dto = new SummaryDTO();
        dto.setTotalInterviews(list.size());
        dto.setHighestScore(list.stream().mapToInt(InterviewSession::getScore).max().orElse(0));
        dto.setLowestScore(list.stream().mapToInt(InterviewSession::getScore).min().orElse(0));
        double avg = list.stream().mapToInt(InterviewSession::getScore).average().orElse(0);
        dto.setAverageScore(avg);
        if(avg == 100){
            dto.setPerformance("Excellent Performance");
        }
        else if(avg >= 80){
            dto.setPerformance("Good Performance");
        }
        else{
            dto.setPerformance("Needs Improvement");
        }
        return dto;
    }
    public InterviewSession submitFinalInterview(String email, InterviewSessionDTO dto){
        InterviewSession session = new InterviewSession();
        session.setUserEmail(email);
        session.setTopic(dto.getTopic());
        session.setDifficulty(dto.getDifficulty());
        session.setScore(dto.getScore());
        session.setAttemptedQuestions(dto.getAttemptedQuestions());
        session.setTotalQuestions(dto.getTotalQuestions());
        return repo.save(session);
    }
    public List<InterviewSession> getAllSessions(){
        return repo.findAll();
    }
    public List<UserSummaryDTO> getAllSummary() {

        List<InterviewSession> allSessions = repo.findAll();

        Map<String, List<InterviewSession>> grouped =
                allSessions.stream()
                        .collect(Collectors.groupingBy(InterviewSession::getUserEmail));

        List<UserSummaryDTO> result = new ArrayList<>();

        for(String email : grouped.keySet()) {

            List<InterviewSession> list = grouped.get(email);

            UserSummaryDTO dto = new UserSummaryDTO();

            dto.setUserEmail(email);
            dto.setTotalInterviews(list.size());

            dto.setHighestScore(
                    list.stream()
                            .mapToInt(InterviewSession::getScore)
                            .max()
                            .orElse(0)
            );

            dto.setLowestScore(
                    list.stream()
                            .mapToInt(InterviewSession::getScore)
                            .min()
                            .orElse(0)
            );

            double avg = list.stream()
                    .mapToInt(InterviewSession::getScore)
                    .average()
                    .orElse(0);

            dto.setAverageScore(avg);

            if(avg == 100){
                dto.setPerformance("Excellent Performance");
            }
            else if(avg >= 80){
                dto.setPerformance("Good Performance");
            }
            else{
                dto.setPerformance("Needs Improvement");
            }

            result.add(dto);
        }

        return result;
    }
}
