package com.lavanya.resume_screening_agent.model;

public class JobDescription {

    private String title;
    private String requiredSkills;
    private String requiredEducation;
    private String requiredExperience;
    private String description;

    public JobDescription() {
    }

    public JobDescription(String title, String requiredSkills,
                          String requiredEducation,
                          String requiredExperience,
                          String description) {
        this.title = title;
        this.requiredSkills = requiredSkills;
        this.requiredEducation = requiredEducation;
        this.requiredExperience = requiredExperience;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(String requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public String getRequiredEducation() {
        return requiredEducation;
    }

    public void setRequiredEducation(String requiredEducation) {
        this.requiredEducation = requiredEducation;
    }

    public String getRequiredExperience() {
        return requiredExperience;
    }

    public void setRequiredExperience(String requiredExperience) {
        this.requiredExperience = requiredExperience;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}