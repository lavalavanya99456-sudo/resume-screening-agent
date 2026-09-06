package com.lavanya.resume_screening_agent.scoring;

import com.lavanya.resume_screening_agent.model.Candidate;
import com.lavanya.resume_screening_agent.model.JobDescription;

import java.util.*;
import java.util.stream.Collectors;

public class RelevanceScorer {

    public double calculateSkillScore(
            Candidate candidate,
            JobDescription jobDescription) {

        String candidateText = candidate.getSkills();
        String requiredText = jobDescription.getRequiredSkills();

        return calculateSimilarity(candidateText, requiredText);
    }

    public double calculateEducationScore(
            Candidate candidate,
            JobDescription jobDescription) {

        String candidateEducation = candidate.getEducation();
        String requiredEducation = jobDescription.getRequiredEducation();

        if (isEmpty(candidateEducation) || isEmpty(requiredEducation)) {
            return 0.0;
        }

        return calculateSimilarity(
                candidateEducation,
                requiredEducation
        );
    }

    public double calculateExperienceScore(
            Candidate candidate,
            JobDescription jobDescription) {

        String candidateExperience = candidate.getExperience();
        String requiredExperience = jobDescription.getRequiredExperience();

        if (isEmpty(candidateExperience) || isEmpty(requiredExperience)) {
            return 0.0;
        }

        return calculateSimilarity(
                candidateExperience,
                requiredExperience
        );
    }

    public double calculateOverallScore(
            Candidate candidate,
            JobDescription jobDescription) {

        double skillScore =
                calculateSkillScore(candidate, jobDescription);

        double educationScore =
                calculateEducationScore(candidate, jobDescription);

        double experienceScore =
                calculateExperienceScore(candidate, jobDescription);

        /*
         * Weighting:
         * Skills      = 50%
         * Education   = 20%
         * Experience  = 30%
         */

        return (skillScore * 0.50)
                + (educationScore * 0.20)
                + (experienceScore * 0.30);
    }

    private double calculateSimilarity(
            String candidateText,
            String jobText) {

        if (isEmpty(candidateText) || isEmpty(jobText)) {
            return 0.0;
        }

        List<String> candidateWords =
                tokenize(candidateText);

        List<String> jobWords =
                tokenize(jobText);

        if (candidateWords.isEmpty() || jobWords.isEmpty()) {
            return 0.0;
        }

        Set<String> vocabulary =
                new HashSet<>();

        vocabulary.addAll(candidateWords);
        vocabulary.addAll(jobWords);

        Map<String, Double> candidateVector =
                createTfIdfVector(
                        candidateWords,
                        candidateWords,
                        jobWords,
                        vocabulary
                );

        Map<String, Double> jobVector =
                createTfIdfVector(
                        jobWords,
                        candidateWords,
                        jobWords,
                        vocabulary
                );

        double similarity =
                cosineSimilarity(
                        candidateVector,
                        jobVector
                );

        return similarity * 100.0;
    }

    private List<String> tokenize(String text) {

        return Arrays.stream(
                        text.toLowerCase()
                                .replaceAll("[^a-z0-9+#.]", " ")
                                .split("\\s+")
                )
                .map(String::trim)
                .filter(word -> word.length() > 1)
                .collect(Collectors.toList());
    }

    private Map<String, Double> createTfIdfVector(
            List<String> currentDocument,
            List<String> document1,
            List<String> document2,
            Set<String> vocabulary) {

        Map<String, Double> vector =
                new HashMap<>();

        for (String word : vocabulary) {

            double tf =
                    calculateTermFrequency(
                            word,
                            currentDocument
                    );

            double idf =
                    calculateIdf(
                            word,
                            document1,
                            document2
                    );

            vector.put(
                    word,
                    tf * idf
            );
        }

        return vector;
    }

    private double calculateTermFrequency(
            String word,
            List<String> document) {

        if (document.isEmpty()) {
            return 0.0;
        }

        long count =
                document.stream()
                        .filter(word::equals)
                        .count();

        return (double) count / document.size();
    }

    private double calculateIdf(
            String word,
            List<String> document1,
            List<String> document2) {

        int documentsContainingWord = 0;

        if (document1.contains(word)) {
            documentsContainingWord++;
        }

        if (document2.contains(word)) {
            documentsContainingWord++;
        }

        int totalDocuments = 2;

        return Math.log(
                (double) totalDocuments
                        / (1 + documentsContainingWord)
        ) + 1;
    }

    private double cosineSimilarity(
            Map<String, Double> vector1,
            Map<String, Double> vector2) {

        double dotProduct = 0.0;
        double magnitude1 = 0.0;
        double magnitude2 = 0.0;

        for (String word : vector1.keySet()) {

            double value1 =
                    vector1.getOrDefault(word, 0.0);

            double value2 =
                    vector2.getOrDefault(word, 0.0);

            dotProduct += value1 * value2;
            magnitude1 += value1 * value1;
            magnitude2 += value2 * value2;
        }

        if (magnitude1 == 0.0 || magnitude2 == 0.0) {
            return 0.0;
        }

        return dotProduct /
                (Math.sqrt(magnitude1)
                        * Math.sqrt(magnitude2));
    }

    private boolean isEmpty(String text) {
        return text == null || text.isBlank();
    }
}