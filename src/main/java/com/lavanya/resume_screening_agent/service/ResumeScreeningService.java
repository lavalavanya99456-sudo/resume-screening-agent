package com.lavanya.resume_screening_agent.service;

import com.lavanya.resume_screening_agent.model.Candidate;
import com.lavanya.resume_screening_agent.model.JobDescription;
import com.lavanya.resume_screening_agent.model.ScreeningResult;
import com.lavanya.resume_screening_agent.parser.ResumeParser;
import com.lavanya.resume_screening_agent.scoring.RelevanceScorer;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class ResumeScreeningService {

    private final ResumeParser resumeParser;
    private final RelevanceScorer relevanceScorer;

    public ResumeScreeningService() {
        this.resumeParser = new ResumeParser();
        this.relevanceScorer = new RelevanceScorer();
    }


    public String extractResumeText(File file) throws IOException {
        return resumeParser.extractText(file);
    }


    public ScreeningResult screenCandidate(Candidate candidate,
                                           JobDescription jobDescription) {

        double skillScore =
                relevanceScorer.calculateSkillScore(candidate, jobDescription);

        double educationScore =
                relevanceScorer.calculateEducationScore(candidate, jobDescription);

        double experienceScore =
                relevanceScorer.calculateExperienceScore(candidate, jobDescription);

        double overallScore =
                relevanceScorer.calculateOverallScore(candidate, jobDescription);

        String reasoning = generateReasoning(
                candidate,
                skillScore,
                educationScore,
                experienceScore,
                overallScore
        );

        return new ScreeningResult(
                candidate.getName(),
                skillScore,
                educationScore,
                experienceScore,
                overallScore,
                reasoning
        );
    }


    private String generateReasoning(Candidate candidate,
                                     double skillScore,
                                     double educationScore,
                                     double experienceScore,
                                     double overallScore) {

        return String.format(
                "Candidate %s has a skill match of %.1f%%, " +
                        "education match of %.1f%%, and experience match of %.1f%%. " +
                        "Overall relevance score is %.1f%%.",
                candidate.getName(),
                skillScore,
                educationScore,
                experienceScore,
                overallScore
        );
    }
}