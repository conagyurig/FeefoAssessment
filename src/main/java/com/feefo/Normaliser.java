package com.feefo;

import java.util.*;

import org.apache.commons.text.similarity.LevenshteinDistance;

public class Normaliser {
    private final List<String> normalizedTitles = Arrays.asList("Architect", "Software engineer", "Quantity surveyor", "Accountant");
    private final LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
    private final Double threshold = 0.2;

    /**
     * Normalizes a given job title to a standardized format based on a predefined list of titles.
     * The normalization process involves calculating the similarity between the input title and each
     * normalized title using the Levenshtein distance metric. A score is derived from this distance to
     * determine the closeness of the match.
     *
     * @param jobTitle The job title to be normalised. Cannot be null.
     * @return The normalised job title if a match is found. Returns "Unknown" if the input is an empty string or no match reaches the threshold.
     * @throws IllegalArgumentException if jobTitle is null, indicating that a valid job title must be provided.
     *
     * The similarity threshold is used to decide if a match is considered valid. This threshold
     * is a distance between 0 and 1, where a smaller distance indicates a closer match. A match is considered
     * valid if its score is less than the threshold value.
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
                // Calculate the normalized Levenshtein score as a fraction of the maximum possible distance
                int maxDiff = Math.max(normalizedWord.length(), inputWord.length());
                bestScore = Math.min(bestScore, (double) levenshteinDistance.apply(inputWord, normalizedWord) / maxDiff);
            }
        }
        return bestScore;
    }
}
