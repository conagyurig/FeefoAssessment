package com.feefo;

import java.util.*;

import org.apache.commons.text.similarity.LevenshteinDistance;

public class Normaliser {
    private final List<String> normalizedTitles = Arrays.asList("Architect", "Software engineer", "Quantity surveyor", "Accountant");
    private final LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
    private final Double threshold = 0.2;

    /**
     * Normalises a given job title to a standardised format.
     *
     * @param jobTitle The job title to be normalised. Cannot be null.
     * @return The normalised job title if a match is found. Returns "Unknown" if the input is an empty string or no match reaches the threshold.
     * @throws IllegalArgumentException if jobTitle is null, indicating that a valid job title must be provided.
     */
    public String normalise(String jobTitle) throws IllegalArgumentException {
        final String defaultTitle = "Unknown";

        if (jobTitle == null) {
            throw new IllegalArgumentException("Job title cannot be null.");
        } else if (jobTitle.isEmpty()) {
            return defaultTitle;
        }

        jobTitle = jobTitle.toLowerCase();

        double minLevScore = 1;
        String bestLevMatch = defaultTitle;
        for (String normalizedTitle : normalizedTitles) {
            double score = calculateLevScore(jobTitle, normalizedTitle.toLowerCase());
            if (score < minLevScore) {
                minLevScore = score;
                bestLevMatch = normalizedTitle;
            }
        }

        return minLevScore < threshold ? bestLevMatch : defaultTitle;
    }

    private double calculateLevScore(String inputTitle, String normalizedTitle) {
        String[] inputWords = inputTitle.split("\\s+|-|\\.");
        String[] normalizedWords = normalizedTitle.split("\\s+|-|\\.");

        double bestScore = 1;
        for (String inputWord : inputWords) {
            for (String normalizedWord : normalizedWords) {
                int maxDiff = Math.max(normalizedWord.length(), inputWord.length()); //max levenshtein distance is the length of the longest word
                bestScore = Math.min(bestScore, (double) levenshteinDistance.apply(inputWord, normalizedWord) / maxDiff);
            }
        }
        return bestScore;
    }
}
