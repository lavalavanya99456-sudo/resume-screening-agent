# Scoring Method

The Resume Screening Agent ranks candidates by comparing their resume information with the requirements in the job description.

## Score Components

| Component | Weight |
|---|---:|
| Skills | 50% |
| Education | 20% |
| Experience | 30% |

## 1. Skill Score — 50%

The candidate's listed skills are compared with the required skills in the job description.

TF-IDF is used to represent the text, followed by cosine similarity to calculate how closely the two texts match.

Skills receive the highest weight because technical skills are the most important factor for this Junior Java Developer role.

## 2. Education Score — 20%

The candidate's education information is compared with the required education.

For example, a Bachelor's degree is compared with the job requirement for a Bachelor's degree.

## 3. Experience Score — 30%

The candidate's experience information is compared with the experience requirement in the job description.

Internships, projects, and other relevant experience can contribute to the textual similarity.

## Overall Score

The final score is calculated as:

```text
Overall Score =
    (Skill Score × 0.50)
  + (Education Score × 0.20)
  + (Experience Score × 0.30)