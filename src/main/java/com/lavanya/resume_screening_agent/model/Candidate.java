package com.lavanya.resume_screening_agent.model;

public class Candidate {

    private String name;
    private String skills;
    private String education;
    private String experience;
    private String resumeText;

    public Candidate() {
    }

    public Candidate(String name, String skills, String education,
                     String experience, String resumeText) {
        this.name = name;
        this.skills = skills;
        this.education = education;
        this.experience = experience;
        this.resumeText = resumeText;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getResumeText() {
        return resumeText;
    }

    public void setResumeText(String resumeText) {
        this.resumeText = resumeText;
    }
}
