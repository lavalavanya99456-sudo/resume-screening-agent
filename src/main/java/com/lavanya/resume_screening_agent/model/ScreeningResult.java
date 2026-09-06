package com.lavanya.resume_screening_agent.model;

public class ScreeningResult {

    private String candidateName;
    private double skillScore;
    private double educationScore;
    private double experienceScore;
    private double overallScore;
    private String reasoning;

    public ScreeningResult() {
    }

    public ScreeningResult(String candidateName,
                           double skillScore,
                           double educationScore,
                           double experienceScore,
                           double overallScore,
                           String reasoning) {
        this.candidateName = candidateName;
        this.skillScore = skillScore;
        this.educationScore = educationScore;
        this.experienceScore = experienceScore;
        this.overallScore = overallScore;
        this.reasoning = reasoning;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public double getSkillScore() {
        return skillScore;
    }

    public void setSkillScore(double skillScore) {
        this.skillScore = skillScore;
    }

    public double getEducationScore() {
        return educationScore;
    }

    public void setEducationScore(double educationScore) {
        this.educationScore = educationScore;
    }

    public double getExperienceScore() {
        return experienceScore;
    }

    public void setExperienceScore(double experienceScore) {
        this.experienceScore = experienceScore;
    }

    public double getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(double overallScore) {
        this.overallScore = overallScore;
    }

    public String getReasoning() {
        return reasoning;
    }

    public void setReasoning(String reasoning) {
        this.reasoning = reasoning;
    }
}
