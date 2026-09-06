package com.lavanya.resume_screening_agent.controller;

import com.lavanya.resume_screening_agent.model.Candidate;
import com.lavanya.resume_screening_agent.model.JobDescription;
import com.lavanya.resume_screening_agent.model.RankedCandidate;
import com.lavanya.resume_screening_agent.model.ScreeningResult;
import com.lavanya.resume_screening_agent.service.CandidateRankingService;
import com.lavanya.resume_screening_agent.service.ResumeScreeningService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    private final ResumeScreeningService resumeScreeningService;
    private final CandidateRankingService candidateRankingService;

    public ResumeController(
            ResumeScreeningService resumeScreeningService,
            CandidateRankingService candidateRankingService) {

        this.resumeScreeningService = resumeScreeningService;
        this.candidateRankingService = candidateRankingService;
    }

    @PostMapping("/parse")
    public String parseResume(
            @RequestParam("file") MultipartFile file) throws IOException {

        File tempFile = File.createTempFile(
                "resume-",
                "-" + file.getOriginalFilename()
        );

        file.transferTo(tempFile);

        try {
            return resumeScreeningService.extractResumeText(tempFile);
        } finally {
            tempFile.delete();
        }
    }

    @PostMapping("/screen")
    public ScreeningResult screenCandidate(
            @RequestBody ScreeningRequest request) {

        Candidate candidate = request.getCandidate();
        JobDescription jobDescription = request.getJobDescription();

        return resumeScreeningService.screenCandidate(
                candidate,
                jobDescription
        );
    }

    @PostMapping("/rank")
    public List<RankedCandidate> rankCandidates(
            @RequestParam("resumesFolder") String resumesFolderPath,
            @RequestParam("jobDescription") String jobDescriptionPath)
            throws IOException {

        File resumesFolder = new File(resumesFolderPath);
        File jobDescriptionFile = new File(jobDescriptionPath);

        return candidateRankingService.rankCandidates(
                resumesFolder,
                jobDescriptionFile
        );
    }

    public static class ScreeningRequest {

        private Candidate candidate;
        private JobDescription jobDescription;

        public ScreeningRequest() {
        }

        public Candidate getCandidate() {
            return candidate;
        }

        public void setCandidate(Candidate candidate) {
            this.candidate = candidate;
        }

        public JobDescription getJobDescription() {
            return jobDescription;
        }

        public void setJobDescription(JobDescription jobDescription) {
            this.jobDescription = jobDescription;
        }
    }
}