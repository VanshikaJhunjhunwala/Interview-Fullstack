package com.example.interview.controller;

import com.example.interview.dto.InterviewSessionDTO;
import com.example.interview.dto.SummaryDTO;
import com.example.interview.dto.UserSummaryDTO;
import com.example.interview.entity.InterviewSession;
import com.example.interview.service.InterviewSessionService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.interview.dto.DashboardDTO;
@RestController
@RequestMapping("/interview")
public class InterviewSessionController {
    private final InterviewSessionService service;
    public InterviewSessionController(InterviewSessionService service){
        this.service=service;
    }
    @PostMapping("/start")
    public InterviewSession start(Authentication auth, @Valid @RequestBody InterviewSessionDTO dto){
        return service.startInterview(auth.getName(), dto);
    }
    @GetMapping("/my-history")
    public List<InterviewSession> history(Authentication auth){
        return service.getMySessions(auth.getName());
    }
    @GetMapping("/dashboard")
    public DashboardDTO dashboard(Authentication auth){
        return service.getDashboard(auth.getName());
    }
    @GetMapping("/summary")
    public SummaryDTO summary(Authentication auth){
        return service.getSummary(auth.getName());
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all-summary")
    public List<UserSummaryDTO> allSummary(){
        return service.getAllSummary();
    }
    @PostMapping("/submit")
    public InterviewSession submitInterview(Authentication auth, @RequestBody InterviewSessionDTO dto){
        return service.submitFinalInterview(auth.getName(),dto);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all-history")
    public List<InterviewSession> allHistory(){
        return service.getAllSessions();
    }
}
