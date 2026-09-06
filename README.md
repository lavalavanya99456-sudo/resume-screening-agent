# AI Resume Screening Agent

An AI-powered resume screening agent built using Java and Spring Boot. The agent parses resumes, extracts candidate information, compares candidates against a job description using NLP-based similarity, calculates relevance scores, and ranks candidates automatically.

## Features

- Supports PDF, DOCX, and TXT resumes
- Extracts:
    - Candidate name
    - Education
    - Skills
    - Experience
- Compares candidate information with job requirements
- Uses TF-IDF and cosine similarity for NLP-based relevance scoring
- Calculates separate scores for:
    - Skills
    - Education
    - Experience
- Calculates an overall candidate relevance score
- Ranks 10+ candidates from highest to lowest score
- Generates reasoning for each candidate
- Produces both JSON and CSV outputs

## Technology Stack

- Java 21
- Spring Boot
- Maven
- Apache PDFBox
- Apache POI
- Jackson
- TF-IDF
- Cosine Similarity
- Postman for API testing

## Project Structure

```text
resume-screening-agent/
│
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── lavanya/
│                   └── resume_screening_agent/
│                       ├── controller/
│                       ├── model/
│                       ├── parser/
│                       ├── scoring/
│                       └── service/
│
├── sample-data/
│   ├── job-description.json
│   ├── candidate01.txt
│   ├── candidate02.txt
│   ├── candidate03.txt
│   ├── candidate04.txt
│   ├── candidate05.txt
│   ├── candidate06.txt
│   ├── candidate07.txt
│   ├── candidate08.txt
│   ├── candidate09.txt
│   └── candidate10.txt
│
├── output/
│   ├── ranked_candidates.json
│   └── ranked_candidates.csv
│
├── pom.xml
└── README.md
└── SCORING_METHOD.md

