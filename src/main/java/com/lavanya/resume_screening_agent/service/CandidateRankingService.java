package com.lavanya.resume_screening_agent.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lavanya.resume_screening_agent.model.Candidate;
import com.lavanya.resume_screening_agent.model.JobDescription;
import com.lavanya.resume_screening_agent.model.RankedCandidate;
import com.lavanya.resume_screening_agent.model.ScreeningResult;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class CandidateRankingService {

    private final ResumeScreeningService resumeScreeningService;
    private final ObjectMapper objectMapper;

    public CandidateRankingService(
            ResumeScreeningService resumeScreeningService) {

        this.resumeScreeningService = resumeScreeningService;
        this.objectMapper = new ObjectMapper();
    }

    public List<RankedCandidate> rankCandidates(
            File resumesFolder,
            File jobDescriptionFile) throws IOException {

        JobDescription jobDescription =
                objectMapper.readValue(
                        jobDescriptionFile,
                        JobDescription.class
                );

        List<RankedCandidate> rankedCandidates =
                new ArrayList<>();

        File[] resumeFiles = resumesFolder.listFiles();

        if (resumeFiles == null) {
            return rankedCandidates;
        }

        for (File resumeFile : resumeFiles) {

            if (!resumeFile.isFile()) {
                continue;
            }

            String fileName =
                    resumeFile.getName().toLowerCase();

            if (!fileName.endsWith(".txt")
                    && !fileName.endsWith(".pdf")
                    && !fileName.endsWith(".docx")) {
                continue;
            }

            Candidate candidate =
                    createCandidateFromResume(resumeFile);

            ScreeningResult result =
                    resumeScreeningService.screenCandidate(
                            candidate,
                            jobDescription
                    );

            RankedCandidate rankedCandidate =
                    new RankedCandidate(
                            0,
                            result.getCandidateName(),
                            result.getSkillScore(),
                            result.getEducationScore(),
                            result.getExperienceScore(),
                            result.getOverallScore(),
                            result.getReasoning()
                    );

            rankedCandidates.add(rankedCandidate);
        }

        // Sort candidates from highest score to lowest score
        rankedCandidates.sort(
                Comparator.comparingDouble(
                        RankedCandidate::getOverallScore
                ).reversed()
        );

        // Assign ranks
        int rank = 1;

        for (RankedCandidate candidate : rankedCandidates) {
            candidate.setRank(rank);
            rank++;
        }

        // Create output folder
        File outputDirectory =
                new File("output");

        if (!outputDirectory.exists()) {
            outputDirectory.mkdirs();
        }

        // Save JSON output
        File jsonFile =
                new File(
                        outputDirectory,
                        "ranked_candidates.json"
                );

        objectMapper.writerWithDefaultPrettyPrinter()
                .writeValue(
                        jsonFile,
                        rankedCandidates
                );

        // Save CSV output
        File csvFile =
                new File(
                        outputDirectory,
                        "ranked_candidates.csv"
                );

        writeCsv(rankedCandidates, csvFile);

        return rankedCandidates;
    }

    private void writeCsv(
            List<RankedCandidate> candidates,
            File csvFile) throws IOException {

        try (FileWriter writer = new FileWriter(csvFile)) {

            writer.write(
                    "Rank,Candidate Name,Skill Score,Education Score," +
                            "Experience Score,Overall Score,Reasoning\n"
            );

            for (RankedCandidate candidate : candidates) {

                writer.write(
                        candidate.getRank() + "," +
                                escapeCsv(candidate.getCandidateName()) + "," +
                                String.format("%.2f", candidate.getSkillScore()) + "," +
                                String.format("%.2f", candidate.getEducationScore()) + "," +
                                String.format("%.2f", candidate.getExperienceScore()) + "," +
                                String.format("%.2f", candidate.getOverallScore()) + "," +
                                escapeCsv(candidate.getReasoning()) +
                                "\n"
                );
            }
        }
    }

    private String escapeCsv(String value) {

        if (value == null) {
            return "";
        }

        return "\"" +
                value.replace("\"", "\"\"") +
                "\"";
    }

    private Candidate createCandidateFromResume(
            File resumeFile) throws IOException {

        String resumeText =
                resumeScreeningService.extractResumeText(
                        resumeFile
                );

        String name =
                extractField(resumeText, "Name:");

        String education =
                extractField(resumeText, "Education:");

        String skills =
                extractField(resumeText, "Skills:");

        String experience =
                extractField(resumeText, "Experience:");

        return new Candidate(
                name,
                skills,
                education,
                experience,
                resumeText
        );
    }

    private String extractField(
            String text,
            String fieldName) {

        String[] lines =
                text.split("\\r?\\n");

        for (int i = 0; i < lines.length; i++) {

            String line =
                    lines[i].trim();

            if (line.toLowerCase()
                    .startsWith(fieldName.toLowerCase())) {

                String value =
                        line.substring(
                                fieldName.length()
                        ).trim();

                if (!value.isEmpty()) {
                    return value;
                }

                if (i + 1 < lines.length) {
                    return lines[i + 1].trim();
                }
            }
        }

        return "";
    }
}