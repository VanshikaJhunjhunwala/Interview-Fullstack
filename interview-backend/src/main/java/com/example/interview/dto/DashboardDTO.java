package com.example.interview.dto;

public class DashboardDTO {
    private int totalInterviews;
    private double averageScore;
    private String weakArea;
    private String performance;

    public int getTotalInterviews() {
        return totalInterviews;
    }

    public void setTotalInterviews(int totalInterviews) {
        this.totalInterviews = totalInterviews;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public String getWeakArea() {
        return weakArea;
    }

    public void setWeakArea(String weakArea) {
        this.weakArea = weakArea;
    }

    public String getPerformance() {
        return performance;
    }

    public void setPerformance(String performance) {
        this.performance = performance;
    }
}
